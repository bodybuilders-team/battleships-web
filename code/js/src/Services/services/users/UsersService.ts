import {RegisterOutput} from "./models/register/RegisterOutput";
import {LoginOutput} from "./models/login/LoginOutput";
import {LogoutOutput} from "./models/logout/LogoutOutput";
import {get, post} from "../../utils/fetchSiren";
import {GetUsersOutput} from "./models/getUsers/GetUsersOutput";
import {SirenEntity} from "../../media/siren/SirenEntity";


export namespace UsersService {

    /**
     * Gets the user home.
     *
     * @param userHomeLink the link to the user home endpoint
     *
     * @return the API result of the get user home request
     */
    export async function getUserHome(userHomeLink: string): Promise<SirenEntity<void>> {
        return await get(userHomeLink);
    }

    /**
     * Gets all the users.
     *
     * @param listUsersLink the link to the list users endpoint
     *
     * @return the API result of the get users request
     */
    export async function getUsers(listUsersLink: string): Promise<GetUsersOutput> {
        return await get(listUsersLink);
    }

    /**
     * Registers the user with the given email, username and password.
     *
     * @param registerLink the link to the register endpoint
     * @param email the email of the user
     * @param username the username of the user
     * @param password the password of the user
     *
     * @return the API result of the register request
     */
    export async function register(
        registerLink: string,
        email: string,
        username: string,
        password: string
    ): Promise<RegisterOutput> {
        return await post(registerLink, JSON.stringify({email, username, password}));
    }

    /**
     * Logs in the user with the given username and password.
     *
     * @param loginLink the link to the login endpoint
     * @param username the username of the user
     * @param password the password of the user
     *
     * @return the API result of the login request
     */
    export async function login(
        loginLink: string,
        username: string,
        password: string
    ): Promise<LoginOutput> {
        return await post(loginLink, JSON.stringify({username, password}));
    }

    /**
     * Logs the user out.
     *
     * @param logoutLink the link to the logout endpoint
     * @param refreshToken the refresh token of the user
     *
     * @return the API result of the logout request
     */
    export async function logout(
        logoutLink: string,
        refreshToken: string
    ): Promise<LogoutOutput> {
        return await post(logoutLink, JSON.stringify({refreshToken}));
    }
}
