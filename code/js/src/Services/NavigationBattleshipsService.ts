import {BattleshipsService} from "./BattleshipsService"
import {NavigationGamesService} from "./services/games/NavigationGamesService"
import NavigationUsersService from "./services/users/NavigationUsersService"
import {NavigationPlayersService} from "./services/games/NavigationPlayersService"
import {useNavigationState} from "../Utils/navigation/NavigationState"
import {useMemo} from "react"
import {Session, SessionManager, useSessionManager} from "../Utils/Session"
import {GetHomeOutput} from "./services/home.models/getHome/GetHomeOutput"
import {throwError} from "./utils/errorUtils"
import {executeRequest} from "./utils/executeRequestUtils"


/**
 * Service to navigate through the battleships API.
 *
 * @property usersService the service that handles the users
 * @property gamesService the service that handles the games
 * @property playersService the service that handles the players
 */
export default class NavigationBattleshipsService {
    readonly usersService: NavigationUsersService
    readonly gamesService: NavigationGamesService
    readonly playersService: NavigationPlayersService
    public readonly links: Map<string, string>
    protected sessionManager: SessionManager

    constructor(links: Map<string, string>, sessionManager: SessionManager) {
        this.links = links
        this.sessionManager = sessionManager
        this.usersService = new NavigationUsersService(this, sessionManager)
        this.gamesService = new NavigationGamesService(this, sessionManager)
        this.playersService = new NavigationPlayersService(this, sessionManager)
    }

    protected get session(): Session {
        return this.sessionManager.session ?? throwError("Session not found")
    }

    /**
     * Gets the home information.
     *
     * @param signal the signal to cancel the request
     *
     * @return the API result of the get home request
     */
    async getHome(signal?: AbortSignal): Promise<GetHomeOutput> {
        const res = await executeRequest(() => BattleshipsService.getHome(signal), signal)

        res.getActionLinks()
            .forEach((value, key) => {
                this.links.set(key, value)
            })

        return res
    }


}

/**
 * Creates a new instance of the NavigationBattleshipsService
 * based on the current state of the navigation and the session manager.
 *
 * @returns NavigationBattleshipsService
 */
export function useBattleshipsService() {
    const navigationState = useNavigationState()
    const sessionManager = useSessionManager()

    return useMemo(() => {
        return new NavigationBattleshipsService(navigationState.links, sessionManager)
    }, [])
}
