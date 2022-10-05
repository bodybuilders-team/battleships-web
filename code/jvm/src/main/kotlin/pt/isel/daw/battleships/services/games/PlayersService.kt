package pt.isel.daw.battleships.services.games

import org.springframework.stereotype.Service
import pt.isel.daw.battleships.database.model.User
import pt.isel.daw.battleships.database.model.game.Game
import pt.isel.daw.battleships.database.model.ship.Ship
import pt.isel.daw.battleships.database.model.shot.Shot
import pt.isel.daw.battleships.database.repositories.GamesRepository
import pt.isel.daw.battleships.database.repositories.UsersRepository
import pt.isel.daw.battleships.services.exceptions.NotFoundException
import pt.isel.daw.battleships.services.games.dtos.ship.InputShipDTO
import pt.isel.daw.battleships.services.games.dtos.ship.OutputShipDTO
import pt.isel.daw.battleships.services.games.dtos.shot.CoordinateDTO
import pt.isel.daw.battleships.services.games.dtos.shot.InputShotDTO
import pt.isel.daw.battleships.services.games.dtos.shot.OutputShotDTO
import pt.isel.daw.battleships.utils.JwtProvider
import javax.transaction.Transactional


@Service
@Transactional
class PlayersService(
    private val gamesRepository: GamesRepository,
    private val usersRepository: UsersRepository,
    private val jwtProvider: JwtProvider
) {

    /**
     * Gets the ships of the player.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     *
     * @return The ships of the player.
     */
    fun getFleet(token: String, gameId: Int): List<OutputShipDTO> {
        val user = authenticateUser(token)

        val game = getGameById(gameId)

        val player = game.getPlayer(user.username)

        return player.ships.map { OutputShipDTO(it) }
    }

    /**
     * Gets the ships of the opponent. Only gets those that are sunk.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     *
     * @return The ships of the opponent.
     */
    fun getOpponentFleet(token: String, gameId: Int): List<OutputShipDTO> {
        val user = authenticateUser(token)

        val game = getGameById(gameId)

        val opponent = game.getOpponent(user.username)

        return opponent.ships.filter { it.lives == 0 }.map { OutputShipDTO(it) }
    }

    /**
     * Deploys the ships of the player.
     *
     * @param token the token of the user
     * @param gameId The id of the game
     * @param fleet The ships to be deployed
     */
    fun deployFleet(token: String, gameId: Int, fleet: List<InputShipDTO>) {
        val user = authenticateUser(token)

        val game = getGameById(gameId)

        val player = game.getPlayer(user.username)

        if (player.ships.isNotEmpty()) {
            throw IllegalArgumentException("Player already has ships deployed.")
        }

        if (game.config.shipTypes.all { shipType -> fleet.count { shipType.shipName == it.type } == shipType.quantity }
            && game.config.shipTypes.fold(0) { acc, shipType -> acc + shipType.quantity } != fleet.size) {
            throw IllegalArgumentException("Fleet doesn't follow fleet configuration for this game.")
        }

        fleet.forEach { ship ->
            val shipType = game.config.shipTypes.find { it.shipName == ship.type }
                ?: throw IllegalArgumentException("'${ship.type}' is an invalid ship type for this game.")

            player.ships.add(
                Ship(
                    type = shipType,
                    coordinate = ship.coordinate.toCoordinate(),
                    orientation = if (ship.orientation == 'H')
                        Ship.Orientation.HORIZONTAL else
                        Ship.Orientation.VERTICAL,
                    lives = shipType.size
                )
            )
        }
    }

    /**
     * Gets the shots of the player.
     *
     * @param token the token of the user
     * @param gameId The id of the game
     *
     * @return The shots of the player.
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
     * @param gameId The id of the game
     * @return The shots of the opponent.
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
     * @param gameId The id of the game
     *
     * @param inputShotsDTO the shots to be created
     */
    fun createShots(token: String, gameId: Int, inputShotsDTO: List<InputShotDTO>): List<OutputShotDTO> {
        val user = authenticateUser(token)

        val game = getGameById(gameId)

        val player = game.getPlayer(user.username)
        val opponent = game.getOpponent(user.username)

        if (inputShotsDTO.distinctBy { it.coordinate }.size != inputShotsDTO.size) {
            throw IllegalArgumentException("Shots must be to distinct coordinates.")
        }
        if (inputShotsDTO.any {
                it.coordinate in player.shots.map { existingShots ->
                    CoordinateDTO(existingShots.coordinate)
                }
            }) {
            throw IllegalArgumentException("Shots must be to coordinates that have not been shot yet.")
        }

        val shots = inputShotsDTO.map { shotDTO ->
            Shot(
                coordinate = shotDTO.coordinate.toCoordinate(),
                round = game.state.round!!,
                opponent.ships.find { ship ->
                    shotDTO.coordinate in ship.coordinates.map { CoordinateDTO(it) }
                }.let { ship ->
                    if (ship != null) {
                        if (--ship.lives > 0)
                            Shot.ShotResult.HIT
                        else
                            Shot.ShotResult.SUNK
                    } else {
                        Shot.ShotResult.MISS
                    }
                }
            )
        }

        player.shots.addAll(shots)

        return shots.map { OutputShotDTO(it) }
    }

    private fun authenticateUser(token: String): User {
        val tokenPayload = jwtProvider.validateToken(token) ?: throw IllegalStateException("Invalid token")

        return usersRepository.findByUsername(tokenPayload.username) ?: throw NotFoundException("User not found")
    }

    /**
     * Gets a game by id.
     *
     * @param gameId the id of the game.
     *
     * @return the game
     * @throws NotFoundException if the game does not exist.
     */
    private fun getGameById(gameId: Int): Game =
        gamesRepository.findById(gameId) ?: throw NotFoundException("Game with id $gameId not found")
}
