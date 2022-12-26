import to from "../../Utils/await-to";
import {Problem} from "../media/Problem";
import NavigationUsersService from "../services/users/NavigationUsersService";
import {SessionManager} from "../../Utils/Session";
import {NetworkError} from "./fetchSiren";
import {AbortedError, delay} from "../../Utils/abortableUtils";

const RETRY_DELAY = 1000;


/**
 * Executes a request and refreshes the token if necessary.
 *
 * @param usersService the users service
 * @param sessionManager the session manager
 * @param request the request to execute
 * @param signal the signal to cancel the request
 *
 * @returns the result of the request
 */
export async function executeRequestAndRefreshTokenIfNecessary<T>(
    usersService: NavigationUsersService,
    sessionManager: SessionManager,
    request: () => Promise<T>,
    signal?: AbortSignal): Promise<T> {
    const [err, res] = await to(executeRequest(() => request(), signal));

    //Check if the response is 401, and if so, refresh the token
    if (err instanceof Problem && err.status === 401) {
        const res = await executeRequest(() => usersService.refreshToken(
            sessionManager.session!.refreshToken
        ), signal);

        sessionManager.setSession({
            ...sessionManager.session!,
            accessToken: res.properties!.accessToken,
            refreshToken: res.properties!.refreshToken,
        })

        return await executeRequest(() => request(), signal);
    } else if (err)
        throw err;

    return res;
}

export async function executeRequest<T>(request: () => Promise<T>, signal?: AbortSignal): Promise<T> {

    while (true) {
        const data = await to(request());
        const [err, res] = data;

        if (signal?.aborted) throw new AbortedError()

        if (err instanceof NetworkError) {
            await delay(RETRY_DELAY, signal);

            continue;
        }

        if (err) throw err

        return res
    }
}
