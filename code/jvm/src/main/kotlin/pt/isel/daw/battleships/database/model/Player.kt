package pt.isel.daw.battleships.database.model

import pt.isel.daw.battleships.database.model.game.Game
import pt.isel.daw.battleships.database.model.ship.Ship
import pt.isel.daw.battleships.database.model.ship.ShipType
import pt.isel.daw.battleships.dtos.games.CoordinateDTO
import pt.isel.daw.battleships.dtos.games.ship.InputShipDTO
import pt.isel.daw.battleships.dtos.games.shot.InputShotDTO
import pt.isel.daw.battleships.services.exceptions.InvalidShotException
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
 * @property ships the ships that the player has
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
    val points: Int = 0
) {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

    @OneToMany(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "player")
    val ships: MutableList<Ship> = mutableListOf()

    @OneToMany(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "player")
    val shots: MutableList<Shot> = mutableListOf()

    /**
     * Adds a ship to the player.
     *
     * @param shipDTO the ship to add
     * @param shipType the type of the ship
     */
    fun addShip(shipDTO: InputShipDTO, shipType: ShipType) {
        ships.add(
            Ship(
                type = shipType,
                coordinate = shipDTO.coordinate.toCoordinate(),
                orientation = Ship.Orientation.valueOf(shipDTO.orientation),
                lives = shipType.size
            )
        )
    }

    /**
     * Shoots the opponent player.
     *
     * @param opponent the opponent player
     * @param inputShots the shots to make
     *
     * @return the list of shots made
     * @throws InvalidShotException if a shot is invalid
     */
    fun shoot(
        opponent: Player,
        inputShots: List<InputShotDTO>
    ): List<Shot> {
        if (inputShots.distinctBy { it.coordinate }.size != inputShots.size) {
            throw InvalidShotException("Shots must be to distinct coordinates.")
        }

        if (
            inputShots.any { shot ->
                shot.coordinate in shots.map { existingShots -> CoordinateDTO(existingShots.coordinate) }
            }
        ) {
            throw InvalidShotException("Shots must be to coordinates that have not been shot yet.")
        }

        val madeShots = inputShots.map { shotDTO ->
            Shot(
                coordinate = shotDTO.coordinate.toCoordinate(),
                round = game.state.round!!,
                result = opponent.ships
                    .find { ship -> shotDTO.coordinate in ship.coordinates.map { CoordinateDTO(it) } }
                    .let { ship ->
                        when {
                            ship == null -> Shot.ShotResult.MISS
                            ship.lives == 1 -> {
                                ship.lives = 0
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
