package pt.isel.daw.battleships.services.games

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pt.isel.daw.battleships.domain.game.Game
import pt.isel.daw.battleships.domain.game.GameState
import pt.isel.daw.battleships.domain.ship.DeployedShip
import pt.isel.daw.battleships.domain.ship.UndeployedShip
import pt.isel.daw.battleships.repository.UsersRepository
import pt.isel.daw.battleships.repository.games.GamesRepository
import pt.isel.daw.battleships.services.AuthenticatedService
import pt.isel.daw.battleships.services.games.dtos.ship.OutputFleetDTO
import pt.isel.daw.battleships.services.games.dtos.ship.OutputShipDTO
import pt.isel.daw.battleships.services.games.dtos.ship.UndeployedFleetDTO
import pt.isel.daw.battleships.services.games.dtos.shot.InputShotsDTO
import pt.isel.daw.battleships.services.games.dtos.shot.OutputShotDTO
import pt.isel.daw.battleships.services.games.dtos.shot.OutputShotsDTO
import pt.isel.daw.battleships.services.exceptions.FleetDeployTimeExpiredException
import pt.isel.daw.battleships.services.exceptions.InvalidPhaseException
import pt.isel.daw.battleships.services.exceptions.InvalidShipTypeException
import pt.isel.daw.battleships.services.exceptions.NotFoundException
import pt.isel.daw.battleships.services.exceptions.ShootTimeExpiredException
import pt.isel.daw.battleships.utils.JwtProvider
import java.sql.Timestamp
import java.time.Instant

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

        return OutputFleetDTO(ships = player.deployedShips.map { OutputShipDTO(it) })
    }

    override fun deployFleet(token: String, gameId: Int, fleetDTO: UndeployedFleetDTO) {
        val user = authenticateUser(token)
        val game = getGameById(gameId)
        val player = game.getPlayer(user.username)
        val opponent = game.getOpponent(user.username)

        if (game.state.phase != GameState.GamePhase.GRID_LAYOUT) {
            game.state.phase = GameState.GamePhase.FINISHED
            game.state.winner = opponent
            throw InvalidPhaseException("Game is not in the placing ships phase")
        }

        if (game.state.phaseEndTime.time < System.currentTimeMillis()) {
            throw FleetDeployTimeExpiredException("The fleet deploy time has expired.")
        }

        val undeployedShips = fleetDTO.ships.map { shipDTO ->
            val shipType = game.config.shipTypes.find { it.shipName == shipDTO.type }
                ?: throw InvalidShipTypeException("Ship type '${shipDTO.type}' is invalid.")

            UndeployedShip(
                shipType,
                shipDTO.coordinate.toCoordinate(),
                DeployedShip.Orientation.values().find { it.name == shipDTO.orientation }
                    ?: throw InvalidShipTypeException("Ship orientation '${shipDTO.orientation}' is invalid.")
            )
        }

        player.deployFleet(undeployedShips)

        if (game.areFleetsDeployed()) {
            game.state.phase = GameState.GamePhase.IN_PROGRESS
            game.state.phaseEndTime = Timestamp.from(Instant.now().plusSeconds(game.config.maxTimePerRound.toLong()))
        }
    }

    override fun getOpponentFleet(token: String, gameId: Int): OutputFleetDTO {
        val user = authenticateUser(token)
        val game = getGameById(gameId).also { it.getPlayer(user.username) }
        val opponent = game.getOpponent(user.username)

        return OutputFleetDTO(
            ships = opponent.deployedShips
                .filter(DeployedShip::isSunk)
                .map { OutputShipDTO(it) }
        )
    }

    override fun getShots(token: String, gameId: Int): OutputShotsDTO {
        val user = authenticateUser(token)
        val game = getGameById(gameId)
        val player = game.getPlayer(user.username)

        return OutputShotsDTO(shots = player.shots.map { OutputShotDTO(it) })
    }

    override fun shoot(token: String, gameId: Int, inputShotsDTO: InputShotsDTO): OutputShotsDTO {
        val user = authenticateUser(token)
        val game = getGameById(gameId)
        val player = game.getPlayer(user.username)
        val opponent = game.getOpponent(user.username)

        if (game.state.phase != GameState.GamePhase.IN_PROGRESS) {
            throw InvalidPhaseException("Game is not in progress.")
        }

        if (game.state.phaseEndTime.time < System.currentTimeMillis()) {
            game.state.phase = GameState.GamePhase.FINISHED
            game.state.winner = opponent

            throw ShootTimeExpiredException("The shoot time has expired.")
        }

        val shots = player.shoot(opponent, inputShotsDTO.shots)

        if (opponent.deployedShips.all(DeployedShip::isSunk)) {
            game.state.phase = GameState.GamePhase.FINISHED
            game.state.winner = player
        } else {
            game.state.phaseEndTime = Timestamp.from(Instant.now().plusSeconds(game.config.maxTimePerRound.toLong()))
        }

        return OutputShotsDTO(shots = shots.map { OutputShotDTO(it) })
    }

    override fun getOpponentShots(token: String, gameId: Int): OutputShotsDTO {
        val user = authenticateUser(token)
        val game = getGameById(gameId).also { it.getPlayer(user.username) }
        val opponent = game.getOpponent(user.username)

        return OutputShotsDTO(shots = opponent.shots.map { OutputShotDTO(it) })
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
