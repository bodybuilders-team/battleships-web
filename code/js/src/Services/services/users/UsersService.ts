import {RegisterOutput} from "./models/register/RegisterOutput";
import {LoginOutput} from "./models/login/LoginOutput";
import {LogoutOutput} from "./models/logout/LogoutOutput";
import {get, post} from "../../utils/fetchSiren";
import {GetUsersOutput} from "./models/getUsers/GetUsersOutput";
import {SirenEntity} from "../../media/siren/SirenEntity";
import {RefreshTokenOutput} from "./models/refreshToken/RefreshTokenOutput";

export namespace UsersService {

    /**
     * Gets the user home.
     *
     * @param userHomeLink the link to the user home endpoint
     * @param signal the signal to cancel the request
     *
     * @return the API result of the get user home request
     */
    export async function getUserHome(userHomeLink: string, signal?: AbortSignal): Promise<SirenEntity<void>> {
        return await get(userHomeLink);
    }

    /**
     * Gets all the users.
     *
     * @param listUsersLink the link to the list users endpoint
     * @param signal the signal to cancel the request
     *
     * @return the API result of the get users request
     */
    export async function getUsers(listUsersLink: string, signal?: AbortSignal): Promise<GetUsersOutput> {
        return await get(listUsersLink, signal);
    }

    /**
     * Registers the user with the given email, username and password.
     *
     * @param registerLink the link to the register endpoint
     * @param email the email of the user
     * @param username the username of the user
     * @param password the password of the user
     * @param signal the signal to cancel the request
     *
     * @return the API result of the register request
     */
    export async function register(
        registerLink: string,
        email: string,
        username: string,
        password: string,
        signal?: AbortSignal
    ): Promise<RegisterOutput> {
        return await post(registerLink, JSON.stringify({email, username, password}), signal);
    }

    /**
     * Logs in the user with the given username and password.
     *
     * @param loginLink the link to the login endpoint
     * @param username the username of the user
     * @param password the password of the user
     * @param signal the signal to cancel the request
     *
     * @return the API result of the login request
     */
    export async function login(
        loginLink: string,
        username: string,
        password: string,
        signal?: AbortSignal
    ): Promise<LoginOutput> {
        return await post(loginLink, JSON.stringify({username, password}), signal);
    }

    /**
     * Logs the user out.
     *
     * @param logoutLink the link to the logout endpoint
     * @param refreshToken the refresh token of the user
     * @param signal the signal to cancel the request
     *
     * @return the API result of the logout request
     */
    export async function logout(
        logoutLink: string,
        refreshToken: string,
        signal?: AbortSignal
    ): Promise<LogoutOutput> {
        return await post(logoutLink, JSON.stringify({refreshToken}), signal);
    }

    /**
     * Refreshes the access token of the user.
     * @param refreshToken the refresh token of the user
     * @param signal the signal to cancel the request
     *
     * @return the API result of the refresh token request
     */
    export function refreshToken(refreshTokenLink: string, refreshToken: string, signal?: AbortSignal): Promise<RefreshTokenOutput> {
        return post(refreshTokenLink, JSON.stringify({refreshToken}), signal);
    }


}
