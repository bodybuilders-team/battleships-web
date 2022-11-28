import {get} from "./utils/fetchSiren";
import {GetHomeOutput} from "./home.models/getHome/GetHomeOutput";

/**
 * Gets the home information.
 *
 * @return the API result of the get home request
 */
export async function getHome(): Promise<GetHomeOutput> {
    return await get("/");
}
