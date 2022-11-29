import {getWithAuth, postWithAuth} from "../utils/fetchSiren";
import {GetMyFleetOutput} from "./models/players/getMyFleet/GetMyFleetOutput";
import {DeployFleetOutput} from "./models/players/deployFleet/DeployFleetOutput";
import {GetOpponentFleetOutput} from "./models/players/getOpponentFleet/GetOpponentFleetOutput";
import {GetMyShotsOutput} from "./models/players/getMyShots/GetMyShotsOutput";
import {FireShotsOutput} from "./models/players/fireShots/FireShotsOutput";
import {GetOpponentShotsOutput} from "./models/players/getOpponentShots/GetOpponentShotsOutput";

/**
 * Gets my fleet.
 *
 * @param token the token of the user
 * @param getMyFleetLink the link to the get my fleet endpoint
 *
 * @return the API result of the get my fleet request
 */
export async function getMyFleet(
    token: string,
    getMyFleetLink: string
): Promise<GetMyFleetOutput> {
    return await getWithAuth(getMyFleetLink, token);
}

/**
 * Deploys the fleet.
 *
 * @param token the token of the user
 * @param deployFleetLink the link to the deploy fleet endpoint
 * @param fleet the fleet to deploy
 *
 * @return the API result of the deploy fleet request
 */
export async function deployFleet(
    token: string,
    deployFleetLink: string,
    fleet: DeployFleetInput
): Promise<DeployFleetOutput> {
    return await postWithAuth(deployFleetLink, token, JSON.stringify(fleet));
}

/**
 * Gets the opponent fleet.
 *
 * @param token the token of the user
 * @param getOpponentFleetLink the link to the get opponent fleet endpoint
 *
 * @return the API result of the get opponent fleet request
 */
export async function getOpponentFleet(
    token: string,
    getOpponentFleetLink: string
): Promise<GetOpponentFleetOutput> {
    return await getWithAuth(getOpponentFleetLink, token);
}

/**
 * Gets my shots.
 *
 * @param token the token of the user
 * @param getMyShotsLink the link to the get my shots endpoint
 *
 * @return the API result of the get my shots request
 */
export async function getMyShots(
    token: string,
    getMyShotsLink: string
): Promise<GetMyShotsOutput> {
    return await getWithAuth(getMyShotsLink, token);
}

/**
 * Fires a list of shots.
 *
 * @param token the token of the user
 * @param fireShotsLink the link to the fire shots endpoint
 * @param shots the shots to fire
 *
 * @return the API result of the fire shots request
 */
export async function fireShots(
    token: string,
    fireShotsLink: string,
    shots: FireShotsInput
): Promise<FireShotsOutput> {
    return await postWithAuth(fireShotsLink, token, JSON.stringify(shots));
}

/**
 * Gets the opponent shots.
 *
 * @param token the token of the user
 * @param getOpponentShotsLink the link to the get opponent shots endpoint
 *
 * @return the API result of the get opponent shots request
 */
export async function getOpponentShots(
    token: string,
    getOpponentShotsLink: string
): Promise<GetOpponentShotsOutput> {
    return await getWithAuth(getOpponentShotsLink, token);
}
