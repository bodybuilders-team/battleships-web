package pt.isel.daw.battleships.domain

import pt.isel.daw.battleships.domain.game.Game
import pt.isel.daw.battleships.domain.ship.DeployedShip
import pt.isel.daw.battleships.domain.ship.Ship.Orientation
import pt.isel.daw.battleships.domain.ship.UndeployedShip
import pt.isel.daw.battleships.service.exceptions.FleetAlreadyDeployedException
import pt.isel.daw.battleships.service.exceptions.InvalidFleetException
import pt.isel.daw.battleships.service.exceptions.InvalidShipTypeException
import pt.isel.daw.battleships.service.exceptions.InvalidShotException
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

/**
 * The Player entity.
 *
 * @property id the id of the player
 * @property game the game that the player is playing
 * @property user the user that is playing the game
 * @property points the points that the player has
 * @property deployedShips the ships that the player has
 * @property shots the shots that the player has made
 */
@Entity
@Table(name = "players")
class Player(
    @ManyToOne
    @JoinColumn(name = "game", nullable = false)
    val game: Game,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(name = "points", nullable = false)
    var points: Int = 0
) {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @OneToMany(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "player")
    val deployedShips: MutableList<DeployedShip> = mutableListOf()

    @OneToMany(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "player")
    val shots: MutableList<Shot> = mutableListOf()

    /**
     * Deploys a fleet of ships.
     *
     * @param undeployedShips the ships to deploy
     */
    fun deployFleet(undeployedShips: List<UndeployedShip>) {
        val config = game.config

        if (deployedShips.isNotEmpty()) {
            throw FleetAlreadyDeployedException("Player has already deployed a fleet")
        }

        if (config.testShipTypes(undeployedShips)) {
            throw InvalidFleetException("Invalid fleet for this game configuration.")
        }

        undeployedShips.forEach { undeployedShip ->
            if (!isValidShipCoordinate(
                    undeployedShip.coordinate,
                    undeployedShip.orientation,
                    undeployedShip.type.size
                )
            ) {
                throw InvalidShipTypeException("Ship is out of bounds")
            }

            deployedShips.forEach { ship ->
                if (ship.isOverlapping(undeployedShip)) {
                    throw InvalidShipTypeException("Ship is overlapping another ship")
                }
            }

            deployedShips.add(
                DeployedShip(
                    player = this,
                    type = undeployedShip.type,
                    coordinate = undeployedShip.coordinate,
                    orientation = undeployedShip.orientation,
                    lives = undeployedShip.type.size
                )
            )
        }
    }

    /**
     * Checks if the given coordinate is valid for the given ship information.
     *
     * @param coordinate the coordinate
     * @param orientation the orientation of the ship
     * @param size the size of the ship
     *
     * @return true if the coordinate is valid, false otherwise
     */
    private fun isValidShipCoordinate(
        coordinate: Coordinate,
        orientation: Orientation,
        size: Int
    ): Boolean {
        val colsRange = game.config.colsRange()
        val rowsRange = game.config.rowsRange()

        return (
            orientation == Orientation.HORIZONTAL &&
                (coordinate.col + size - 1) in colsRange &&
                coordinate.row in rowsRange
            ) || (
            orientation == Orientation.VERTICAL &&
                (coordinate.row + size - 1) in rowsRange &&
                coordinate.col in colsRange
            )
    }

    /**
     * Shoots the opponent player.
     *
     * @param opponent the opponent player
     * @param coordinates the coordinates of the shots
     *
     * @return the list of shots made
     * @throws InvalidShotException if a shot is invalid
     */
    fun shoot(
        opponent: Player,
        coordinates: List<Coordinate>
    ): List<Shot> {
        if (!coordinates.all { it.col in game.config.colsRange() && it.row in game.config.rowsRange() }) {
            throw InvalidShotException("Shot is out of bounds")
        }

        if (coordinates.distinctBy { it }.size != coordinates.size) {
            throw InvalidShotException("Shots must have distinct coordinates.")
        }

        if (
            coordinates.any { coordinate ->
                coordinate in shots.map { existingShots ->
                    existingShots.coordinate
                }
            }
        ) {
            throw InvalidShotException("Shots must be to coordinates that have not been shot yet.")
        }

        val madeShots = coordinates.map { coordinate ->
            Shot(
                player = this,
                coordinate = coordinate,
                round = game.state.round ?: throw IllegalStateException("Game round cannot be null"),
                result = opponent.deployedShips
                    .find { ship ->
                        coordinate in ship.coordinates.map { it }
                    }
                    .let { ship ->
                        when {
                            ship == null -> Shot.ShotResult.MISS
                            ship.lives == 1 -> {
                                ship.lives = 0
                                points += ship.type.points

                                Shot.ShotResult.SUNK
                            }
                            else -> {
                                ship.lives--

                                Shot.ShotResult.HIT
                            }
                        }
                    }
            )
        }

        this.shots.addAll(madeShots)
        return madeShots
    }
}
