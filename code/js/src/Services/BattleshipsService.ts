import {get} from "./utils/fetchSiren"
import {GetHomeOutput} from "./services/home.models/getHome/GetHomeOutput"

export const API_ENDPOINT = "http://localhost:80/api"

export namespace BattleshipsService {

    /**
     * Gets the home information.
     *
     * @param signal the signal to abort the request
     *
     * @return the API result of the get home request
     */
    export async function getHome(signal?: AbortSignal): Promise<GetHomeOutput> {
        return await get("/", signal)
    }
}
