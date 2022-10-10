package pt.isel.daw.battleships.services.games

import pt.isel.daw.battleships.services.exceptions.FleetAlreadyDeployedException
import pt.isel.daw.battleships.services.exceptions.FleetDeployTimeExpiredException
import pt.isel.daw.battleships.services.exceptions.InvalidFleetException
import pt.isel.daw.battleships.services.exceptions.InvalidShipTypeException
import pt.isel.daw.battleships.services.exceptions.InvalidShotException
import pt.isel.daw.battleships.services.exceptions.ShootTimeExpiredException
import pt.isel.daw.battleships.services.games.dtos.ship.InputFleetDTO
import pt.isel.daw.battleships.services.games.dtos.ship.OutputFleetDTO
import pt.isel.daw.battleships.services.games.dtos.shot.InputShotsDTO
import pt.isel.daw.battleships.services.games.dtos.shot.OutputShotsDTO

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
     */
    fun getFleet(token: String, gameId: Int): OutputFleetDTO

    /**
     * Gets the fleet of the opponent.
     * Only gets those that are sunk.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     *
     * @return the fleet of the opponent
     */
    fun getOpponentFleet(token: String, gameId: Int): OutputFleetDTO

    /**
     * Deploys the fleet of the player.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     * @param fleetDTO the ships to be deployed
     *
     * @throws FleetAlreadyDeployedException if the fleet is already deployed
     * @throws InvalidFleetException if the fleet is invalid
     * @throws InvalidShipTypeException if the ship type is invalid
     * @throws FleetDeployTimeExpiredException if the fleet deploy time has expired
     */
    fun deployFleet(token: String, gameId: Int, fleetDTO: InputFleetDTO)

    /**
     * Gets the shots of the player.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     *
     * @return te shots of the player
     */
    fun getShots(token: String, gameId: Int): OutputShotsDTO

    /**
     * Gets the shots of the opponent.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     *
     * @return the shots of the opponent
     */
    fun getOpponentShots(token: String, gameId: Int): OutputShotsDTO

    /**
     * Shoots at the opponent.
     *
     * @param token the token of the user
     * @param gameId the id of the game
     * @param inputShotsDTO the shots to be shot
     *
     * @return the shots shot
     * @throws InvalidShotException if a shot is invalid
     * @throws ShootTimeExpiredException if the player tries to shoot after the shoot time has expired
     */
    fun shoot(token: String, gameId: Int, inputShotsDTO: InputShotsDTO): OutputShotsDTO
}
