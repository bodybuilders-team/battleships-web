import {get, post} from "../../utils/fetchSiren"
import {GetMyFleetOutput} from "./models/players/getMyFleet/GetMyFleetOutput"
import {DeployFleetOutput} from "./models/players/deployFleet/DeployFleetOutput"
import {GetOpponentFleetOutput} from "./models/players/getOpponentFleet/GetOpponentFleetOutput"
import {GetMyShotsOutput} from "./models/players/getMyShots/GetMyShotsOutput"
import {FireShotsOutput} from "./models/players/fireShots/FireShotsOutput"
import {GetOpponentShotsOutput} from "./models/players/getOpponentShots/GetOpponentShotsOutput"
import {DeployFleetInput} from "./models/players/deployFleet/DeployFleetInput"

export namespace PlayersService {

    /**
     * Gets my fleet.
     *
     * @param getMyFleetLink the link to the get my fleet endpoint
     * @param signal the signal to cancel the request
     *
     * @return the API result of the get my fleet request
     */
    export async function getMyFleet(
        getMyFleetLink: string,
        signal?: AbortSignal
    ): Promise<GetMyFleetOutput> {
        return await get(getMyFleetLink, signal)
    }

    /**
     * Deploys the fleet.
     *
     * @param deployFleetLink the link to the deploy fleet endpoint
     * @param fleet the fleet to deploy
     * @param signal the signal to cancel the request
     *
     * @return the API result of the deploy fleet request
     */
    export async function deployFleet(
        deployFleetLink: string,
        fleet: DeployFleetInput,
        signal?: AbortSignal
    ): Promise<DeployFleetOutput> {
        return await post(deployFleetLink, JSON.stringify(fleet), signal)
    }

    /**
     * Gets the opponent fleet.
     *
     * @param getOpponentFleetLink the link to the get opponent fleet endpoint
     * @param signal the signal to cancel the request
     *
     * @return the API result of the get opponent fleet request
     */
    export async function getOpponentFleet(
        getOpponentFleetLink: string,
        signal?: AbortSignal
    ): Promise<GetOpponentFleetOutput> {
        return await get(getOpponentFleetLink, signal)
    }

    /**
     * Gets my shots.
     *
     * @param getMyShotsLink the link to the get my shots endpoint
     * @param signal the signal to cancel the request
     *
     * @return the API result of the get my shots request
     */
    export async function getMyShots(
        getMyShotsLink: string,
        signal?: AbortSignal
    ): Promise<GetMyShotsOutput> {
        return await get(getMyShotsLink, signal)
    }

    /**
     * Fires a list of shots.
     *
     * @param fireShotsLink the link to the fire shots endpoint
     * @param shots the shots to fire
     * @param signal the signal to cancel the request
     *
     * @return the API result of the fire shots request
     */
    export async function fireShots(
        fireShotsLink: string,
        shots: FireShotsInput,
        signal?: AbortSignal
    ): Promise<FireShotsOutput> {
        return await post(fireShotsLink, JSON.stringify(shots), signal)
    }

    /**
     * Gets the opponent shots.
     *
     * @param getOpponentShotsLink the link to the get opponent shots endpoint
     * @param signal the signal to cancel the request
     *
     * @return the API result of the get opponent shots request
     */
    export async function getOpponentShots(
        getOpponentShotsLink: string,
        signal?: AbortSignal
    ): Promise<GetOpponentShotsOutput> {
        return await get(getOpponentShotsLink, signal)
    }
}
