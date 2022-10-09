package pt.isel.daw.battleships.services.games

import org.springframework.stereotype.Service
import pt.isel.daw.battleships.database.model.Shot
import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.model.game.Game
import pt.isel.daw.battleships.database.model.ship.Ship
import pt.isel.daw.battleships.database.repositories.GamesRepository
import pt.isel.daw.battleships.database.repositories.UsersRepository
import pt.isel.daw.battleships.services.exceptions.AuthenticationException
import pt.isel.daw.battleships.services.exceptions.InvalidArgumentException
import pt.isel.daw.battleships.services.exceptions.NotFoundException
import pt.isel.daw.battleships.services.games.dtos.CoordinateDTO
import pt.isel.daw.battleships.services.games.dtos.ship.InputShipDTO
import pt.isel.daw.battleships.services.games.dtos.ship.OutputShipDTO
import pt.isel.daw.battleships.services.games.dtos.shot.InputShotDTO
import pt.isel.daw.battleships.services.games.dtos.shot.OutputShotDTO
import pt.isel.daw.battleships.utils.JwtProvider
import javax.transaction.Transactional

/**
 * Service that handles the business logic of the players.
 *
 * @property gamesRepository the repository of the games
 * @property usersRepository the repository of the users
 * @property jwtProvider the JWT provider
 */
@Service
@Transactional
class PlayersService(
    private val gamesRepository: GamesRepository,
    private val usersRepository: UsersRepository,
    private val jwtProvider: JwtProvider
) {

    /**
     * Gets the fleet of a player.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     *
     * @return the fleet of the player
     */
    fun getFleet(token: String, gameId: Int): List<OutputShipDTO> {
        val user = authenticateUser(token)
        val game = getGameById(gameId)
        val player = game.getPlayer(user.username)

        return player.ships.map { OutputShipDTO(it) }
    }

    /**
     * Gets the fleet of the opponent.
     * Only gets those that are sunk.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     *
     * @return the fleet of the opponent
     */
    fun getOpponentFleet(token: String, gameId: Int): List<OutputShipDTO> {
        val user = authenticateUser(token)
        val game = getGameById(gameId)
        val opponent = game.getOpponent(user.username)

        return opponent.ships
            .filter { it.lives == 0 }
            .map { OutputShipDTO(it) }
    }

    /**
     * Deploys the fleet of the player.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     * @param fleet the ships to be deployed
     *
     * @throws InvalidArgumentException if the player already has a fleet or if the fleet is invalid
     */
    fun deployFleet(token: String, gameId: Int, fleet: List<InputShipDTO>) {
        val user = authenticateUser(token)
        val game = getGameById(gameId)
        val player = game.getPlayer(user.username)

        if (player.ships.isNotEmpty()) {
            throw InvalidArgumentException("Player already deployed ships.")
        }

        if (
            game.config.shipTypes.all { shipType -> fleet.count { shipType.shipName == it.type } == shipType.quantity } &&
            game.config.shipTypes.fold(0) { acc, shipType -> acc + shipType.quantity } != fleet.size
        ) {
            throw InvalidArgumentException("Fleet doesn't follow fleet configuration for this game.")
        }

        fleet.forEach { ship ->
            val shipType = game.config.shipTypes.find { it.shipName == ship.type }
                ?: throw InvalidArgumentException("'${ship.type}' is an invalid ship type for this game.")

            player.ships.add(
                Ship(
                    type = shipType,
                    coordinate = ship.coordinate.toCoordinate(),
                    orientation = if (ship.orientation == 'H') {
                        Ship.Orientation.HORIZONTAL
                    } else {
                        Ship.Orientation.VERTICAL
                    },
                    lives = shipType.size
                )
            )
        }
    }

    /**
     * Gets the shots of the player.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     *
     * @return te shots of the player
     */
    fun getShots(token: String, gameId: Int): List<OutputShotDTO> {
        val user = authenticateUser(token)
        val game = getGameById(gameId)
        val player = game.getPlayer(user.username)

        return player.shots.map { OutputShotDTO(it) }
    }

    /**
     * Gets the shots of the opponent.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     *
     * @return the shots of the opponent
     */
    fun getOpponentShots(token: String, gameId: Int): List<OutputShotDTO> {
        val user = authenticateUser(token)
        val game = getGameById(gameId)
        val opponent = game.getOpponent(user.username)

        return opponent.shots.map { OutputShotDTO(it) }
    }

    /**
     * Creates the shots of the player.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     * @param inputShotsDTO the shots to be created
     *
     * @return the shots created
     * @throws InvalidArgumentException if the shots are invalid
     */
    fun createShots(token: String, gameId: Int, inputShotsDTO: List<InputShotDTO>): List<OutputShotDTO> {
        val user = authenticateUser(token)
        val game = getGameById(gameId)
        val player = game.getPlayer(user.username)
        val opponent = game.getOpponent(user.username)

        if (inputShotsDTO.distinctBy { it.coordinate }.size != inputShotsDTO.size) {
            throw InvalidArgumentException("Shots must be to distinct coordinates.")
        }

        if (
            inputShotsDTO.any {
                it.coordinate in player.shots.map { existingShots -> CoordinateDTO(existingShots.coordinate) }
            }
        ) {
            throw InvalidArgumentException("Shots must be to coordinates that have not been shot yet.")
        }

        val shots = inputShotsDTO.map { shotDTO ->
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

        player.shots.addAll(shots)

        return shots.map { OutputShotDTO(it) }
    }

    /**
     * Authenticates a user.
     *
     * @param token the token of the user
     *
     * @return the authenticated user
     * @throws AuthenticationException if the token is invalid
     * @throws NotFoundException if the user is not found
     */
    private fun authenticateUser(token: String): User {
        val tokenPayload = jwtProvider.validateToken(token)
            ?: throw AuthenticationException("Invalid token")

        return usersRepository.findByUsername(tokenPayload.username)
            ?: throw NotFoundException("User not found")
    }

    /**
     * Gets a game by id.
     *
     * @param gameId the id of the game
     *
     * @return the game
     * @throws NotFoundException if the game does not exist
     */
    private fun getGameById(gameId: Int): Game =
        gamesRepository.findById(gameId)
            ?: throw NotFoundException("Game with id $gameId not found")
}
