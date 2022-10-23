package pt.isel.daw.battleships.service.games

import pt.isel.daw.battleships.domain.exceptions.FiringShotsTimeExpiredException
import pt.isel.daw.battleships.domain.exceptions.FleetDeployTimeExpiredException
import pt.isel.daw.battleships.domain.exceptions.UserNotInGameException
import pt.isel.daw.battleships.service.exceptions.AuthenticationException
import pt.isel.daw.battleships.service.exceptions.FleetAlreadyDeployedException
import pt.isel.daw.battleships.service.exceptions.InvalidFleetException
import pt.isel.daw.battleships.service.exceptions.InvalidPhaseException
import pt.isel.daw.battleships.service.exceptions.InvalidShipTypeException
import pt.isel.daw.battleships.service.exceptions.InvalidShotException
import pt.isel.daw.battleships.service.exceptions.InvalidTurnException
import pt.isel.daw.battleships.service.exceptions.NotFoundException
import pt.isel.daw.battleships.service.games.dtos.ship.DeployedFleetDTO
import pt.isel.daw.battleships.service.games.dtos.ship.UndeployedFleetDTO
import pt.isel.daw.battleships.service.games.dtos.shot.FiredShotsDTO
import pt.isel.daw.battleships.service.games.dtos.shot.UnfiredShotsDTO

/**
 * Service that handles the business logic of the players.
 */
interface PlayersService {

    /**
     * Gets the fleet of a player.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     *
     * @return the fleet of the player
     * @throws AuthenticationException if the token is invalid
     * @throws NotFoundException if the user is not found
     * @throws NotFoundException if the game does not exist
     * @throws UserNotInGameException if the player is not playing the game
     */
    fun getFleet(token: String, gameId: Int): DeployedFleetDTO

    /**
     * Deploys the fleet of the player.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     * @param fleetDTO the ships to be deployed
     *
     * @throws InvalidPhaseException if the game is not in the placing ships phase
     * @throws FleetAlreadyDeployedException if the fleet is already deployed
     * @throws InvalidFleetException if the fleet is invalid
     * @throws InvalidShipTypeException if the ship type is invalid
     * @throws FleetDeployTimeExpiredException if the fleet deploy time has expired@throws AuthenticationException if the token is invalid
     * @throws NotFoundException if the user is not found
     * @throws NotFoundException if the game does not exist
     * @throws UserNotInGameException if the player is not playing the game
     */
    fun deployFleet(token: String, gameId: Int, fleetDTO: UndeployedFleetDTO)

    /**
     * Gets the fleet of the opponent.
     * Only gets those that are sunk.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     *
     * @return the fleet of the opponent
     * @throws AuthenticationException if the token is invalid
     * @throws NotFoundException if the user is not found
     * @throws NotFoundException if the game does not exist
     * @throws UserNotInGameException if the player is not playing the game
     */
    fun getOpponentFleet(token: String, gameId: Int): DeployedFleetDTO

    /**
     * Gets the shots of the player.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     *
     * @return te shots of the player
     * @throws AuthenticationException if the token is invalid
     * @throws NotFoundException if the user is not found
     * @throws NotFoundException if the game does not exist
     * @throws UserNotInGameException if the player is not playing the game
     */
    fun getShots(token: String, gameId: Int): FiredShotsDTO

    /**
     * Shoots at the opponent.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     * @param unfiredShotsDTO the shots to be shot
     *
     * @return the shots shot
     * @throws InvalidPhaseException if the game is not in progress
     * @throws InvalidShotException if a shot is invalid
     * @throws FiringShotsTimeExpiredException if the player tries to shoot after the shoot time has expired
     * @throws AuthenticationException if the token is invalid
     * @throws NotFoundException if the user is not found
     * @throws NotFoundException if the game does not exist
     * @throws UserNotInGameException if the player is not playing the game
     * @throws InvalidTurnException if it's not the player turn
     * @throws IllegalStateException if the round is null
     */
    fun fireShots(token: String, gameId: Int, unfiredShotsDTO: UnfiredShotsDTO): FiredShotsDTO

    /**
     * Gets the shots of the opponent.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     *
     * @return the shots of the opponent
     * @throws AuthenticationException if the token is invalid
     * @throws NotFoundException if the user is not found
     * @throws NotFoundException if the game does not exist
     * @throws UserNotInGameException if the player is not playing the game
     */
    fun getOpponentShots(token: String, gameId: Int): FiredShotsDTO
}
