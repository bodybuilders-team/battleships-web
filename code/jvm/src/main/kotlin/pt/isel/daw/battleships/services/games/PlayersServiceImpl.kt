package pt.isel.daw.battleships.services.games

import org.springframework.stereotype.Service
import pt.isel.daw.battleships.database.model.game.Game
import pt.isel.daw.battleships.database.model.ship.Ship
import pt.isel.daw.battleships.database.repositories.UsersRepository
import pt.isel.daw.battleships.database.repositories.games.GamesRepository
import pt.isel.daw.battleships.services.AuthenticatedService
import pt.isel.daw.battleships.services.exceptions.FleetAlreadyDeployedException
import pt.isel.daw.battleships.services.exceptions.InvalidFleetException
import pt.isel.daw.battleships.services.exceptions.InvalidShipTypeException
import pt.isel.daw.battleships.services.exceptions.NotFoundException
import pt.isel.daw.battleships.services.games.dtos.ship.InputFleetDTO
import pt.isel.daw.battleships.services.games.dtos.ship.OutputFleetDTO
import pt.isel.daw.battleships.services.games.dtos.ship.OutputShipDTO
import pt.isel.daw.battleships.services.games.dtos.shot.InputShotsDTO
import pt.isel.daw.battleships.services.games.dtos.shot.OutputShotDTO
import pt.isel.daw.battleships.services.games.dtos.shot.OutputShotsDTO
import pt.isel.daw.battleships.utils.JwtProvider
import javax.transaction.Transactional

/**
 * Service that handles the business logic of the players.
 *
 * @property gamesRepository the repository of the games
 * @param usersRepository the repository of the users
 * @param jwtProvider the JWT provider
 */
@Service
@Transactional
class PlayersServiceImpl(
    private val gamesRepository: GamesRepository,
    usersRepository: UsersRepository,
    jwtProvider: JwtProvider
) : PlayersService, AuthenticatedService(usersRepository, jwtProvider) {

    override fun getFleet(token: String, gameId: Int): OutputFleetDTO {
        val user = authenticateUser(token)
        val game = getGameById(gameId)
        val player = game.getPlayer(user.username)

        return OutputFleetDTO(ships = player.ships.map { OutputShipDTO(it) })
    }

    override fun getOpponentFleet(token: String, gameId: Int): OutputFleetDTO {
        val user = authenticateUser(token)
        val game = getGameById(gameId).also { it.getPlayer(user.username) }
        val opponent = game.getOpponent(user.username)

        return OutputFleetDTO(
            ships = opponent.ships
                .filter(Ship::isSunk)
                .map { OutputShipDTO(it) }
        )
    }

    override fun deployFleet(token: String, gameId: Int, fleetDTO: InputFleetDTO) {
        val user = authenticateUser(token)
        val game = getGameById(gameId)
        val player = game.getPlayer(user.username)

        if (player.ships.isNotEmpty()) {
            throw FleetAlreadyDeployedException("Player already has a fleet")
        }

        if (game.config.isValidFleet(fleetDTO.ships)) {
            throw InvalidFleetException("Invalid fleet for this game configuration.")
        }

        fleetDTO.ships.forEach { shipDTO ->
            val shipType = game.config.shipTypes.find { it.shipName == shipDTO.type }
                ?: throw InvalidShipTypeException("Ship type '${shipDTO.type}' is invalid.")

            player.addShip(shipDTO, shipType)
        }
    }

    override fun getShots(token: String, gameId: Int): OutputShotsDTO {
        val user = authenticateUser(token)
        val game = getGameById(gameId)
        val player = game.getPlayer(user.username)

        return OutputShotsDTO(shots = player.shots.map { OutputShotDTO(it) })
    }

    override fun getOpponentShots(token: String, gameId: Int): OutputShotsDTO {
        val user = authenticateUser(token)
        val game = getGameById(gameId).also { it.getPlayer(user.username) }
        val opponent = game.getOpponent(user.username)

        return OutputShotsDTO(shots = opponent.shots.map { OutputShotDTO(it) })
    }

    override fun shoot(token: String, gameId: Int, inputShotsDTO: InputShotsDTO): OutputShotsDTO {
        val user = authenticateUser(token)
        val game = getGameById(gameId)
        val player = game.getPlayer(user.username)
        val opponent = game.getOpponent(user.username)

        return OutputShotsDTO(shots = player.shoot(opponent, inputShotsDTO.shots).map { OutputShotDTO(it) })
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
        gamesRepository
            .findById(gameId)
            ?: throw NotFoundException("Game with id $gameId not found")
}
