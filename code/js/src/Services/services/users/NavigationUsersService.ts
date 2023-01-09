import {UsersService} from "./UsersService"
import {Rels} from "../../../Utils/navigation/Rels"
import {throwError} from "../../utils/errorUtils"
import {GetUsersOutput} from "./models/getUsers/GetUsersOutput"
import {GetUsersUserModel} from "./models/getUsers/GetUsersUserModel"
import {SirenEntity} from "../../media/siren/SirenEntity"
import NavigationBattleshipsService from "../../NavigationBattleshipsService"
import {Session, SessionManager} from "../../../Utils/Session"
import {executeRequest} from "../../utils/executeRequestUtils"

/**
 * The service that handles the users, and keeps track of the links to the endpoints.
 */
export default class NavigationUsersService {
    constructor(private battleshipsService: NavigationBattleshipsService, private sessionManager: SessionManager) {
    }

    private get links(): Map<string, string> {
        return this.battleshipsService.links
    }

    private get session(): Session {
        return this.sessionManager.session ?? throwError("Session not found")
    }

    /**
     * Gets the user home information.
     *
     * @param signal the signal to cancel the request
     *
     * @return a promise with the result of the get user home request
     */
    async getUserHome(signal?: AbortSignal): Promise<SirenEntity<void>> {
        if (!this.links.get(Rels.USER_HOME)) {
            this.links.set(Rels.USER_HOME, this.session.userHomeLink)
            await this.battleshipsService.getHome(signal) // Needed in case the user refreshed
        }

        const res = await executeRequest(() => UsersService.getUserHome(
            this.links.get(Rels.USER_HOME) ?? throwError("User home link not found"),
            signal
        ), signal)

        res.getActionLinks().forEach((value, key) => {
            this.links.set(key, value)
        })

        return res
    }

    /**
     * Gets the users information.
     *
     * @param signal the signal to cancel the request
     *
     * @return a promise with the result of the get users request
     */
    async getUsers(signal?: AbortSignal): Promise<GetUsersOutput> {
        if (!this.links.get(Rels.LIST_USERS))
            await this.battleshipsService.getHome(signal)

        const res = await executeRequest(() => UsersService.getUsers(
            this.links.get(Rels.LIST_USERS) ?? throwError("List users link not found"),
            signal
        ), signal)

        res.getEmbeddedSubEntities<GetUsersUserModel>().forEach(entity => {
            const username = entity?.properties?.username ?? throwError("Username not found")
            this.links.set(`${Rels.USER}-${username}`, entity.getLink(Rels.SELF))
        })

        return res
    }

    /**
     * Creates a new user.
     * @param email the email of the user
     * @param username the username of the user
     * @param password the password of the user
     * @param signal the signal to cancel the request
     *
     * @return a promise with the result of the create user request
     */
    async register(email: string, username: string, password: string, signal?: AbortSignal) {
        if (!this.links.get(Rels.REGISTER))
            await this.battleshipsService.getHome(signal)

        const res = await executeRequest(() => UsersService.register(
            this.links.get(Rels.REGISTER) ?? throwError("Register link not found"),
            email,
            username,
            password,
            signal
        ), signal)

        this.links.set(Rels.USER_HOME, res.getLink(Rels.USER_HOME))

        return res
    }

    /**
     * Logs in a user.
     * @param username the username of the user
     * @param password the password of the user
     * @param signal the signal to cancel the request
     *
     * @return a promise with the result of the login request
     */
    async login(username: string, password: string, signal?: AbortSignal) {
        if (!this.links.get(Rels.LOGIN))
            await this.battleshipsService.getHome(signal)

        const res = await executeRequest(() => UsersService.login(
            this.links.get(Rels.LOGIN) ?? throwError("Login link not found"),
            username,
            password,
            signal
        ), signal)

        this.links.set(Rels.USER_HOME, res.getLink(Rels.USER_HOME))

        return res
    }

    /**
     * Logs out a user.
     * @param signal the signal to cancel the request
     *
     * @return a promise with the result of the logout request
     */
    async logout(signal?: AbortSignal) {
        if (!this.links.get(Rels.LOGOUT))
            await this.battleshipsService.usersService.getUserHome(signal)

        await executeRequest(() => UsersService.logout(
            this.links.get(Rels.LOGOUT) ?? throwError("Logout link not found"),
            signal
        ), signal)
    }

    /**
     * Refreshes the token of a user.
     *
     * @param signal the signal to cancel the request
     *
     * @return a promise with the result of the refresh token request
     */
    async refreshToken(signal?: AbortSignal) {
        if (!this.links.get(Rels.REFRESH_TOKEN))
            await this.getUserHome(signal)

        const res = await executeRequest(() => UsersService.refreshToken(
            this.links.get(Rels.REFRESH_TOKEN) ?? throwError("Refresh token link not found"),
            signal
        ), signal)

        this.links.set(Rels.USER_HOME, res.getLink(Rels.USER_HOME))

        return res
    }
}
