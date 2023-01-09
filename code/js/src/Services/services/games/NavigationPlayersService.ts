import {Session, SessionManager} from "../../../Utils/Session"
import {throwError} from "../../utils/errorUtils"
import {GetMyFleetOutput} from "./models/players/getMyFleet/GetMyFleetOutput"
import {Rels} from "../../../Utils/navigation/Rels"
import {PlayersService} from "./PlayersService"
import {DeployFleetOutput} from "./models/players/deployFleet/DeployFleetOutput"
import {GetOpponentFleetOutput} from "./models/players/getOpponentFleet/GetOpponentFleetOutput"
import {GetMyShotsOutput} from "./models/players/getMyShots/GetMyShotsOutput"
import {FireShotsOutput} from "./models/players/fireShots/FireShotsOutput"
import {GetOpponentShotsOutput} from "./models/players/getOpponentShots/GetOpponentShotsOutput"
import NavigationBattleshipsService from "../../NavigationBattleshipsService"
import {DeployFleetInput} from "./models/players/deployFleet/DeployFleetInput"
import {executeRequestAndRefreshTokenIfNecessary} from "../../utils/executeRequestUtils"

/**
 * Service to navigate through the players endpoints.
 */
export class NavigationPlayersService {
    constructor(private battleshipsService: NavigationBattleshipsService, private sessionManager: SessionManager) {

    }

    private get links(): Map<string, string> {
        return this.battleshipsService.links
    }

    private get session(): Session {
        return this.sessionManager.session ?? throwError("Session not found")
    }

    /**
     * Gets the player's fleet.
     *
     * @param signal the signal to cancel the request
     *
     * @return a promise with the API result of the get my fleet request
     */
    async getMyFleet(signal?: AbortSignal): Promise<GetMyFleetOutput> {
        return await executeRequestAndRefreshTokenIfNecessary(
            this.battleshipsService.usersService,
            this.sessionManager,
            () => PlayersService.getMyFleet(
                this.links.get(Rels.GET_MY_FLEET) ?? throwError("Get my fleet link not found"),
                signal
            ),
            signal
        )
    }

    /**
     * Deploys the player's fleet.
     *
     * @param fleet the fleet to deploy
     * @param signal the signal to cancel the request
     *
     * @return a promise with the API result of the deploy fleet request
     */
    async deployFleet(fleet: DeployFleetInput, signal?: AbortSignal): Promise<DeployFleetOutput> {
        return await executeRequestAndRefreshTokenIfNecessary(
            this.battleshipsService.usersService,
            this.sessionManager,
            () => PlayersService.deployFleet(
                this.links.get(Rels.DEPLOY_FLEET) ?? throwError("Deploy fleet link not found"),
                fleet,
                signal
            ),
            signal
        )
    }

    /**
     * Gets the opponent's fleet.
     *
     * @param signal the signal to cancel the request
     *
     * @return a promise with the API result of the get opponent fleet request
     */
    async getOpponentFleet(signal?: AbortSignal): Promise<GetOpponentFleetOutput> {
        return await executeRequestAndRefreshTokenIfNecessary(
            this.battleshipsService.usersService,
            this.sessionManager,
            () => PlayersService.getOpponentFleet(
                this.links.get(Rels.GET_OPPONENT_FLEET)
                ?? throwError("Get opponent fleet link not found"),
                signal
            ),
            signal
        )
    }

    /**
     * Gets the player's shots.
     *
     * @param signal the signal to cancel the request
     *
     * @return a promise with the API result of the get my shots request
     */
    async getMyShots(signal?: AbortSignal): Promise<GetMyShotsOutput> {
        return await executeRequestAndRefreshTokenIfNecessary(
            this.battleshipsService.usersService,
            this.sessionManager,
            () => PlayersService.getMyShots(
                this.links.get(Rels.GET_MY_SHOTS) ?? throwError("Get my shots link not found"),
                signal
            ),
            signal
        )
    }

    /**
     * Fires shots.
     *
     * @param shots the shots to fire
     * @param signal the signal to cancel the request
     *
     * @return a promise with the API result of the fire shots request
     */
    async fireShots(shots: FireShotsInput, signal?: AbortSignal): Promise<FireShotsOutput> {
        return await executeRequestAndRefreshTokenIfNecessary(
            this.battleshipsService.usersService,
            this.sessionManager,
            () => PlayersService.fireShots(
                this.links.get(Rels.FIRE_SHOTS) ?? throwError("Fire shots link not found"),
                shots,
                signal
            ),
            signal
        )
    }

    /**
     * Gets the opponent's shots.
     *
     * @param signal the signal to cancel the request
     *
     * @return a promise with the API result of the get opponent shots request
     */
    async getOpponentShots(signal?: AbortSignal): Promise<GetOpponentShotsOutput> {
        return await executeRequestAndRefreshTokenIfNecessary(
            this.battleshipsService.usersService,
            this.sessionManager,
            () => PlayersService.getOpponentShots(
                this.links.get(Rels.GET_OPPONENT_SHOTS)
                ?? throwError("Get opponent shots link not found"),
                signal
            ),
            signal
        )
    }
}
