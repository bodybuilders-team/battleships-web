package pt.isel.daw.battleships.service.games

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import pt.isel.daw.battleships.domain.exceptions.FiringShotsTimeExpiredException
import pt.isel.daw.battleships.domain.exceptions.FleetDeployTimeExpiredException
import pt.isel.daw.battleships.domain.exceptions.InvalidShipTypeException
import pt.isel.daw.battleships.domain.games.game.Game
import pt.isel.daw.battleships.domain.games.game.GameState
import pt.isel.daw.battleships.domain.games.ship.DeployedShip
import pt.isel.daw.battleships.domain.games.ship.Ship
import pt.isel.daw.battleships.domain.games.ship.UndeployedShip
import pt.isel.daw.battleships.repository.games.GamesRepository
import pt.isel.daw.battleships.repository.users.RevokedAccessTokensRepository
import pt.isel.daw.battleships.repository.users.UsersRepository
import pt.isel.daw.battleships.service.AuthenticatedService
import pt.isel.daw.battleships.service.exceptions.InvalidPhaseException
import pt.isel.daw.battleships.service.exceptions.InvalidTurnException
import pt.isel.daw.battleships.service.exceptions.NotFoundException
import pt.isel.daw.battleships.service.games.dtos.ship.DeployedFleetDTO
import pt.isel.daw.battleships.service.games.dtos.ship.DeployedShipDTO
import pt.isel.daw.battleships.service.games.dtos.ship.UndeployedFleetDTO
import pt.isel.daw.battleships.service.games.dtos.shot.FiredShotsDTO
import pt.isel.daw.battleships.service.games.dtos.shot.UnfiredShotsDTO
import pt.isel.daw.battleships.service.utils.HashingUtils
import pt.isel.daw.battleships.utils.JwtProvider
import javax.persistence.EntityManager
import javax.persistence.LockModeType
import javax.persistence.PersistenceContext

/**
 * Service that handles the business logic of the players.
 *
 * @property gamesRepository the repository of the games
 * @param usersRepository the repository of the users
 * @param jwtProvider the JWT provider
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class PlayersServiceImpl(
    private val gamesRepository: GamesRepository,
    private val usersRepository: UsersRepository,
    private val jwtProvider: JwtProvider,
    revokedAccessTokensRepository: RevokedAccessTokensRepository,
    hashingUtils: HashingUtils
) : PlayersService,
    AuthenticatedService(
        usersRepository,
        revokedAccessTokensRepository,
        jwtProvider,
        hashingUtils
    ) {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun getFleet(token: String, gameId: Int): DeployedFleetDTO {
        val user = authenticateUser(token = token)
        val game = getGameById(gameId = gameId)
        val player = game.getPlayer(username = user.username)

        return DeployedFleetDTO(ships = player.deployedShips.map(::DeployedShipDTO))
    }

    override fun deployFleet(token: String, gameId: Int, fleetDTO: UndeployedFleetDTO) {
        val user = authenticateUser(token = token)
        val game = getGameById(gameId = gameId)
        val player = game.getPlayer(username = user.username)

        if (game.state.phase != GameState.GamePhase.DEPLOYING_FLEETS)
            throw InvalidPhaseException("Game is not in the deploying fleet phase")

        if (game.state.phaseExpired()) {
            game.abortGame(GameState.EndCause.TIMEOUT)
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

        player.deployFleet(undeployedShips = undeployedShips)

        if (game.areFleetsDeployed()) {
            game.updatePhase()
            game.state.turn = game.players.first()
            game.state.round = 1
        }
    }

    override fun getOpponentFleet(token: String, gameId: Int): DeployedFleetDTO {
        val user = authenticateUser(token = token)
        val game = getGameById(gameId = gameId)

        val player = game.getPlayer(username = user.username)
        val opponent = game.getOpponent(player)

        return DeployedFleetDTO(
            ships = opponent.deployedShips
                .filter(DeployedShip::isSunk)
                .map(::DeployedShipDTO)
        )
    }

    override fun getShots(token: String, gameId: Int): FiredShotsDTO {
        val user = authenticateUser(token = token)
        val game = getGameById(gameId = gameId)
        val player = game.getPlayer(username = user.username)

        return FiredShotsDTO(shots = player.shots)
    }

    override fun fireShots(token: String, gameId: Int, unfiredShotsDTO: UnfiredShotsDTO): FiredShotsDTO {
        val user = authenticateUser(token)
        val game = getGameById(gameId)

        val player = game.getPlayer(user.username)
        val opponent = game.getOpponent(player)

        when {
            game.state.phase == GameState.GamePhase.FINISHED &&
                game.state.turn != game.state.winner && game.state.turn == player ->
                throw FiringShotsTimeExpiredException("The firing shots time has expired. Game is finished.")

            game.state.phase != GameState.GamePhase.IN_PROGRESS -> throw InvalidPhaseException("Game is not in progress.")
            game.state.turn != player -> throw InvalidTurnException("It is not your turn")
        }

        val shotsCoordinates = unfiredShotsDTO.shots.map { it.coordinate.toCoordinate() }
        val shots = player.shoot(coordinates = shotsCoordinates)

        if (opponent.deployedShips.all(DeployedShip::isSunk))
            game.finishGame(winner = player, cause = GameState.EndCause.DESTRUCTION)
        else {
            game.updatePhase()

            game.state.turn = opponent

            val currRound = game.state.round ?: throw IllegalStateException("Round is null")
            game.state.round = currRound + 1
        }

        return FiredShotsDTO(shots = shots)
    }

    override fun getOpponentShots(token: String, gameId: Int): FiredShotsDTO {
        val user = authenticateUser(token = token)
        val game = getGameById(gameId = gameId)

        val player = game.getPlayer(username = user.username)
        val opponent = game.getOpponent(player)

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
    private fun getGameById(gameId: Int): Game {
        val game = gamesRepository
            .findById(id = gameId)
            ?: throw NotFoundException("Game with id $gameId not found")

        entityManager.lock(game, LockModeType.PESSIMISTIC_WRITE)

        game.updateIfPhaseExpired()

        return game
    }
}
