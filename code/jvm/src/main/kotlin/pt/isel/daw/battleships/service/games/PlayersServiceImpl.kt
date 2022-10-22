package pt.isel.daw.battleships.service.games

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pt.isel.daw.battleships.domain.game.Game
import pt.isel.daw.battleships.domain.game.GameState
import pt.isel.daw.battleships.domain.ship.DeployedShip
import pt.isel.daw.battleships.domain.ship.Ship
import pt.isel.daw.battleships.domain.ship.UndeployedShip
import pt.isel.daw.battleships.repository.UsersRepository
import pt.isel.daw.battleships.repository.games.GamesRepository
import pt.isel.daw.battleships.service.AuthenticatedService
import pt.isel.daw.battleships.service.exceptions.FleetDeployTimeExpiredException
import pt.isel.daw.battleships.service.exceptions.InvalidPhaseException
import pt.isel.daw.battleships.service.exceptions.InvalidShipTypeException
import pt.isel.daw.battleships.service.exceptions.NotFoundException
import pt.isel.daw.battleships.service.exceptions.ShootTimeExpiredException
import pt.isel.daw.battleships.service.games.dtos.ship.DeployedFleetDTO
import pt.isel.daw.battleships.service.games.dtos.ship.DeployedShipDTO
import pt.isel.daw.battleships.service.games.dtos.ship.UndeployedFleetDTO
import pt.isel.daw.battleships.service.games.dtos.shot.FiredShotsDTO
import pt.isel.daw.battleships.service.games.dtos.shot.UnfiredShotsDTO
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

    override fun getFleet(token: String, gameId: Int): DeployedFleetDTO {
        val user = authenticateUser(token)
        val game = getGameById(gameId)
        val player = game.getPlayer(user.username)

        return DeployedFleetDTO(ships = player.deployedShips.map { DeployedShipDTO(it) })
    }

    override fun deployFleet(token: String, gameId: Int, fleetDTO: UndeployedFleetDTO) {
        val user = authenticateUser(token)
        val game = getGameById(gameId)
        val player = game.getPlayer(username = user.username)
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
                type = shipType,
                coordinate = shipDTO.coordinate.toCoordinate(),
                orientation = Ship.Orientation.valueOf(shipDTO.orientation)
            )
        }

        player.deployFleet(undeployedShips)

        if (game.areFleetsDeployed()) {
            game.state.phase = GameState.GamePhase.IN_PROGRESS
            game.state.phaseEndTime = Timestamp.from(Instant.now().plusSeconds(game.config.maxTimePerRound.toLong()))
        }
    }

    override fun getOpponentFleet(token: String, gameId: Int): DeployedFleetDTO {
        val user = authenticateUser(token)
        val game = getGameById(gameId).also { it.getPlayer(user.username) }
        val opponent = game.getOpponent(user.username)

        return DeployedFleetDTO(
            ships = opponent.deployedShips
                .filter(DeployedShip::isSunk)
                .map { DeployedShipDTO(it) }
        )
    }

    override fun getShots(token: String, gameId: Int): FiredShotsDTO {
        val user = authenticateUser(token)
        val game = getGameById(gameId)
        val player = game.getPlayer(user.username)

        return FiredShotsDTO(shots = player.shots)
    }

    override fun fireShots(token: String, gameId: Int, unfiredShotsDTO: UnfiredShotsDTO): FiredShotsDTO {
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

        val shotsCoordinates = unfiredShotsDTO.shots.map {
            it.coordinate.toCoordinate()
        }

        val shots = player.shoot(opponent, shotsCoordinates)

        if (opponent.deployedShips.all(DeployedShip::isSunk)) {
            game.state.phase = GameState.GamePhase.FINISHED
            game.state.winner = player
        } else {
            game.state.phaseEndTime = Timestamp.from(Instant.now().plusSeconds(game.config.maxTimePerRound.toLong()))
        }

        return FiredShotsDTO(shots = shots)
    }

    override fun getOpponentShots(token: String, gameId: Int): FiredShotsDTO {
        val user = authenticateUser(token)
        val game = getGameById(gameId).also { it.getPlayer(user.username) }
        val opponent = game.getOpponent(user.username)

        return FiredShotsDTO(shots = opponent.shots)
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
