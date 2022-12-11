import {UsersService} from "./UsersService";
import {Rels} from "../../../Utils/navigation/Rels";
import {throwError} from "../../utils/errorUtils";
import {GetUsersOutput} from "./models/getUsers/GetUsersOutput";
import {GetUsersUserModel} from "./models/getUsers/GetUsersUserModel";
import {SirenEntity} from "../../media/siren/SirenEntity";
import NavigationBattleshipsService from "../../NavigationBattleshipsService";
import {Session, SessionManager} from "../../../Utils/Session";

/**
 * The service that handles the users, and keeps track of the links to the endpoints.
 */
export default class NavigationUsersService {
    private get links(): Map<string, string> {
        return this.battleshipsService.links;
    }

    private get session(): Session {
        return this.sessionManager.session ?? throwError("Session not found");
    }

    constructor(private battleshipsService: NavigationBattleshipsService, private sessionManager: SessionManager) {
    }

    /**
     * Gets the user home information.
     *
     * @return a promise with the result of the get user home request
     */
    async getUserHome(): Promise<SirenEntity<void>> {
        if (!this.links.get(Rels.USER_HOME)) {
            this.links.set(Rels.USER_HOME, this.session.userHomeLink);
            await this.battleshipsService.getHome(); // Needed in case the user refreshed
        }

        const res = await UsersService.getUserHome(
            this.links.get(Rels.USER_HOME) ?? throwError("User home link not found")
        );

        res.getActionLinks().forEach((value, key) => {
            this.links.set(key, value);
        });

        return res;
    }

    /**
     * Gets the users information.
     *
     * @return a promise with the result of the get users request
     */
    async getUsers(): Promise<GetUsersOutput> {
        if (!this.links.get(Rels.LIST_USERS))
            await this.battleshipsService.getHome();

        const res = await UsersService.getUsers(
            this.links.get(Rels.LIST_USERS) ?? throwError("List users link not found")
        );

        res.getEmbeddedSubEntities<GetUsersUserModel>().forEach(entity => {
            const username = entity?.properties?.username ?? throwError("Username not found");
            this.links.set(`${Rels.USER}-${username}`, entity.getLink(Rels.SELF));
        });

        return res;
    }

    /**
     * Creates a new user.
     * @param email the email of the user
     * @param username the username of the user
     * @param password the password of the user
     *
     * @return a promise with the result of the create user request
     */
    async register(email: string, username: string, password: string) {
        if (!this.links.get(Rels.REGISTER))
            await this.battleshipsService.getHome();

        const res = await UsersService.register(
            this.links.get(Rels.REGISTER) ?? throwError("Register link not found"),
            email,
            username,
            password
        );

        this.links.set(Rels.USER_HOME, res.getLink(Rels.USER_HOME));

        return res;
    }

    /**
     * Logs in a user.
     * @param username the username of the user
     * @param password the password of the user
     *
     * @return a promise with the result of the login request
     */
    async login(username: string, password: string) {
        if (!this.links.get(Rels.LOGIN))
            await this.battleshipsService.getHome();

        const res = await UsersService.login(
            this.links.get(Rels.LOGIN) ?? throwError("Login link not found"),
            username,
            password
        );

        this.links.set(Rels.USER_HOME, res.getLink(Rels.USER_HOME));

        return res;
    }

    /**
     * Logs out a user.
     * @param refreshToken the refresh token of the user
     *
     * @return a promise with the result of the logout request
     */
    async logout(refreshToken: string) {
        if (!this.links.get(Rels.LOGOUT))
            await this.battleshipsService.getHome();

        await UsersService.logout(
            this.links.get(Rels.LOGOUT) ?? throwError("Logout link not found"),
            refreshToken
        );
    }
}
