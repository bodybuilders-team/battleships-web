import {get} from "./utils/fetchSiren";
import {GetHomeOutput} from "./services/home.models/getHome/GetHomeOutput";

export const API_ENDPOINT = "http://localhost:8080";


export namespace BattleshipsService {

    /**
     * Gets the home information.
     *
     * @return the API result of the get home request
     */
    export async function getHome(): Promise<GetHomeOutput> {
        return await get("/");
    }
}
