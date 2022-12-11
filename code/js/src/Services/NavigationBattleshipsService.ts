import {BattleshipsService} from "./BattleshipsService";
import {NavigationGamesService} from "./services/games/NavigationGamesService";
import NavigationUsersService from "./services/users/NavigationUsersService";
import {NavigationPlayersService} from "./services/games/NavigationPlayersService";
import {useNavigationState} from "../Utils/navigation/NavigationStateProvider";
import * as React from "react";
import {SessionManager, useSessionManager} from "../Utils/Session";
import {GetHomeOutput} from "./services/home.models/getHome/GetHomeOutput";


/**
 * Service to navigate through the battleships API.
 *
 * @property usersService the service that handles the users
 * @property gamesService the service that handles the games
 * @property playersService the service that handles the players
 */
export default class NavigationBattleshipsService {

    readonly usersService: NavigationUsersService;
    readonly gamesService: NavigationGamesService;
    readonly playersService: NavigationPlayersService;

    private readonly _links: Map<string, string>
    public get links(): Map<string, string> {
        return this._links;
    }

    constructor(links: Map<string, string>, private sessionManager: SessionManager) {
        this._links = new Map(links);
        this.usersService = new NavigationUsersService(this, sessionManager);
        this.gamesService = new NavigationGamesService(this, sessionManager);
        this.playersService = new NavigationPlayersService(this, sessionManager);
    }

    /**
     * Gets the home information.
     *
     * @return the API result of the get home request
     */
    async getHome(): Promise<GetHomeOutput> {
        const res = await BattleshipsService.getHome();

        res.getActionLinks()
            .forEach((value, key) => {
                this._links.set(key, value);
            });

        return res;
    }
}

/**
 * Creates a new instance of the NavigationBattleshipsService
 * based on the current state of the navigation and the session manager.
 *
 * @returns NavigationBattleshipsService
 */
export function useBattleshipsService() {
    const navigationState = useNavigationState();
    const sessionManager = useSessionManager();

    return React.useMemo(() => {
        return new NavigationBattleshipsService(navigationState.links, sessionManager);
    }, []);
}
