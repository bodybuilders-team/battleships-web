import {Session, SessionManager} from "../../Utils/Session";
import {throwError} from "../utils/errorUtils";
import {GetMyFleetOutput} from "./models/players/getMyFleet/GetMyFleetOutput";
import {Rels} from "../utils/Rels";
import {PlayersService} from "./PlayersService";
import {DeployFleetOutput} from "./models/players/deployFleet/DeployFleetOutput";
import {GetOpponentFleetOutput} from "./models/players/getOpponentFleet/GetOpponentFleetOutput";
import {GetMyShotsOutput} from "./models/players/getMyShots/GetMyShotsOutput";
import {FireShotsOutput} from "./models/players/fireShots/FireShotsOutput";
import {GetOpponentShotsOutput} from "./models/players/getOpponentShots/GetOpponentShotsOutput";
import NavigationBattleshipsService from "../NavigationBattleshipsService";

/**
 * Service to navigate through the players endpoints.
 */
export class NavigationPlayersService {
    private get links(): Map<string, string> {
        return this.battleshipsService.links;
    }

    private get session(): Session {
        return this.sessionManager.session ?? throwError("Session not found");
    }

    constructor(private battleshipsService: NavigationBattleshipsService, private sessionManager: SessionManager) {
    }

    /**
     * Gets the player's fleet.
     *
     * @return a promise with the API result of the get my fleet request
     */
    async getMyFleet(): Promise<GetMyFleetOutput> {
        return await PlayersService.getMyFleet(
            this.session.accessToken,
            this.links.get(Rels.GET_MY_FLEET)
            ?? throwError("Get my fleet link not found"));
    }

    /**
     * Deploys the player's fleet.
     *
     * @param fleet the fleet to deploy
     *
     * @return a promise with the API result of the deploy fleet request
     */
    async deployFleet(fleet: DeployFleetInput): Promise<DeployFleetOutput> {
        return await PlayersService.deployFleet(
            this.session.accessToken,
            this.links.get(Rels.DEPLOY_FLEET)
            ?? throwError("Deploy fleet link not found"),
            fleet);
    }

    /**
     * Gets the opponent's fleet.
     *
     * @return a promise with the API result of the get opponent fleet request
     */
    async getOpponentFleet(): Promise<GetOpponentFleetOutput> {
        return await PlayersService.getOpponentFleet(
            this.session.accessToken,
            this.links.get(Rels.GET_OPPONENT_FLEET)
            ?? throwError("Get opponent fleet link not found"));
    }

    /**
     * Gets the player's shots.
     *
     * @return a promise with the API result of the get my shots request
     */
    async getMyShots(): Promise<GetMyShotsOutput> {
        return await PlayersService.getMyShots(
            this.session.accessToken,
            this.links.get(Rels.GET_MY_SHOTS)
            ?? throwError("Get my shots link not found"));
    }

    /**
     * Fires shots.
     *
     * @param shots the shots to fire
     *
     * @return a promise with the API result of the fire shots request
     */
    async fireShots(shots: FireShotsInput): Promise<FireShotsOutput> {
        return await PlayersService.fireShots(
            this.session.accessToken,
            this.links.get(Rels.FIRE_SHOTS)
            ?? throwError("Fire shots link not found"),
            shots);
    }

    /**
     * Gets the opponent's shots.
     *
     * @return a promise with the API result of the get opponent shots request
     */
    async getOpponentShots(): Promise<GetOpponentShotsOutput> {
        return await PlayersService.getOpponentShots(
            this.session.accessToken,
            this.links.get(Rels.GET_OPPONENT_SHOTS)
            ?? throwError("Get opponent shots link not found"));
    }
}