import {BattleshipsService} from "./BattleshipsService";
import {NavigationGamesService} from "./games/NavigationGamesService";
import NavigationUsersService from "./users/NavigationUsersService";
import {NavigationPlayersService} from "./games/NavigationPlayersService";
import {useNavigationState} from "../Utils/NavigationStateProvider";
import * as React from "react";
import {SessionManager, useSessionManager} from "../Utils/Session";
import {GetHomeOutput} from "./home.models/getHome/GetHomeOutput";


/**
 * Service to navigate through the battleships API.
 */
export default class NavigationBattleshipsService {
    readonly playersService: NavigationPlayersService;
    readonly usersService: NavigationUsersService;
    readonly gamesService: NavigationGamesService;

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
     * @return a promise with the {GetHomeOutput} result of the get home request
     */
    async getHome(): Promise<GetHomeOutput> {
        const res = await BattleshipsService.getHome();

        res.getActionLinks().forEach((value, key) => {
            this._links.set(key, value);
        })

        return res;
    }
}

/**
 * Creates a new instance of the NavigationBattleshipsService
 * based on the current state of the navigation and the session manager
 *
 * @returns NavigationBattleshipsService
 */
export function useBattleshipsService() {
    const navigationState = useNavigationState()
    const sessionManager = useSessionManager()

    return React.useState<NavigationBattleshipsService>(() => {
        return new NavigationBattleshipsService(navigationState.links, sessionManager);
    })
}
