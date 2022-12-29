import {GetGamesOutput} from "./models/games/getGames/GetGamesOutput"
import {GamesService} from "./GamesService"
import {Rels} from "../../../Utils/navigation/Rels"
import {throwError} from "../../utils/errorUtils"
import {GetGameOutput, GetGameOutputModel} from "./models/games/getGame/GetGameOutput"
import {Session, SessionManager} from "../../../Utils/Session"
import {MatchmakeOutput} from "./models/games/matchmake/MatchmakeOutput"
import {JoinGameOutput} from "./models/games/joinGame/JoinGameOutput"
import {SirenEntity} from "../../media/siren/SirenEntity"
import {GetGameStateOutput} from "./models/games/getGameState/GetGameStateOutput"
import NavigationBattleshipsService from "../../NavigationBattleshipsService"
import {CreateGameInput} from "./models/games/createGame/CreateGameInput"
import {CreateGameOutput} from "./models/games/createGame/CreateGameOutput"
import {executeRequest, executeRequestAndRefreshTokenIfNecessary} from "../../utils/executeRequestUtils"
import GetGamesParams = GamesService.GetGamesParams;


/**
 * Service to navigate through the games endpoints.
 */
export class NavigationGamesService {
    constructor(private battleshipsService: NavigationBattleshipsService, private sessionManager: SessionManager) {
    }

    private get links(): Map<string, string> {
        return this.battleshipsService.links
    }

    private get session(): Session {
        return this.sessionManager.session ?? throwError("Session not found")
    }

    /**
     * Gets the games information.
     *
     * @param getGamesParams the parameters to filter the games
     * @param signal the signal to cancel the request
     *
     * @return a promise with the API result of the get games request
     */
    async getGames(getGamesParams?: GetGamesParams, signal?: AbortSignal): Promise<GetGamesOutput> {
        if (!this.links.has(Rels.LIST_GAMES))
            await this.battleshipsService.usersService.getUserHome(signal)

        const res = await executeRequest(() => GamesService.getGames(
            this.links.get(Rels.LIST_GAMES) ?? throwError("List games link not found"),
            getGamesParams,
            signal
        ), signal)

        res.getEmbeddedSubEntities<GetGameOutputModel>().forEach(entity => {
            const id = entity?.properties?.id ?? throwError("Game id not found")

            this.links.set(`${Rels.GAME}-${id}`, entity.getLink(Rels.SELF))
            this.links.set(`${Rels.JOIN_GAME}-${id}`, entity.getAction(Rels.JOIN_GAME))
            this.links.set(`${Rels.GAME_STATE}-${id}`, entity.getEmbeddedLinks(Rels.GAME_STATE)[0].href)
            console.log(this.links)
        })

        return res
    }

    /**
     * Creates a new game.
     *
     * @param createGameInput the game configuration
     * @param signal the signal to cancel the request
     *
     * @return a promise with the API result of the create game request
     */
    async createGame(createGameInput: CreateGameInput, signal?: AbortSignal): Promise<CreateGameOutput> {
        if (!this.links.has(Rels.CREATE_GAME))
            await this.battleshipsService.usersService.getUserHome(signal)

        const res = await executeRequestAndRefreshTokenIfNecessary(
            this.battleshipsService.usersService,
            this.sessionManager,
            () => GamesService.createGame(
                this.session.accessToken,
                this.links.get(Rels.CREATE_GAME)
                ?? throwError("Create game link not found"),
                createGameInput,
                signal
            ),
            signal)

        this.links.set(Rels.GAME, res.getEmbeddedLinks(Rels.GAME)[0].href)
        this.links.set(Rels.GAME_STATE, res.getEmbeddedLinks(Rels.GAME_STATE)[0].href)

        return res
    }

    /**
     * Matchmakes a game with a specific configuration.
     *
     * @param gameConfig the game's configuration
     * @param signal the signal to cancel the request
     *
     * @return the API result of the matchmake request
     */
    async matchmake(gameConfig: GameConfigModel, signal?: AbortSignal): Promise<MatchmakeOutput> {
        if (!this.links.has(Rels.MATCHMAKE))
            await this.battleshipsService.usersService.getUserHome(signal)

        const res = await executeRequestAndRefreshTokenIfNecessary(
            this.battleshipsService.usersService,
            this.sessionManager,
            () => GamesService.matchmake(
                this.session.accessToken,
                this.links.get(Rels.MATCHMAKE) ?? throwError("Matchmake link not found"),
                gameConfig,
                signal
            ),
            signal
        )


        this.links.set(Rels.GAME, res.getEmbeddedLinks(Rels.GAME)[0].href)
        this.links.set(Rels.GAME_STATE, res.getEmbeddedLinks(Rels.GAME_STATE)[0].href)

        return res
    }

    /**
     * Gets the game information.
     *
     * @param signal the signal to cancel the request
     *
     * @return a promise with the API result of the get game request
     */
    async getGame(signal?: AbortSignal): Promise<GetGameOutput> {
        const res = await executeRequestAndRefreshTokenIfNecessary(
            this.battleshipsService.usersService,
            this.sessionManager,
            () => GamesService.getGame(
                this.session.accessToken,
                this.links.get(Rels.GAME) ?? throwError("Game link not found"),
                signal
            ),
            signal
        )

        this.links.set(Rels.GAME_STATE, res.getEmbeddedLinks(Rels.GAME_STATE)[0].href)
        res.getActionLinks().forEach((value, key) => {
            this.links.set(key, value)
        })

        return res
    }

    /**
     * Gets the state of a game.
     *
     * @param signal the signal to cancel the request
     *
     * @return the API result of the get game state request
     */
    async getGameState(signal?: AbortSignal): Promise<GetGameStateOutput> {
        return await executeRequestAndRefreshTokenIfNecessary(
            this.battleshipsService.usersService,
            this.sessionManager,
            () => GamesService.getGameState(
                this.session.accessToken,
                this.links.get(Rels.GAME_STATE) ?? throwError("Game state link not found"),
                signal
            ),
            signal
        )
    }

    /**
     * Joins a game.
     *
     * @param signal the signal to cancel the request
     *
     * @param joinGameLink the link to the join game endpoint
     * @return the API result of the join game request
     */
    async joinGame(joinGameLink: string, signal?: AbortSignal): Promise<JoinGameOutput> {
        const res = await executeRequestAndRefreshTokenIfNecessary(
            this.battleshipsService.usersService,
            this.sessionManager,
            () => GamesService.joinGame(
                this.session.accessToken,
                joinGameLink,
                signal
            ),
            signal
        )

        this.links.set(Rels.GAME, res.getEmbeddedLinks(Rels.GAME)[0].href)
        this.links.set(Rels.GAME_STATE, res.getEmbeddedLinks(Rels.GAME_STATE)[0].href)

        return res
    }


    /**
     * Leaves a game.
     *
     * @param signal the signal to cancel the request
     *
     * @return the API result of the leave game request
     */
    async leaveGame(signal?: AbortSignal): Promise<SirenEntity<void>> {
        return await executeRequestAndRefreshTokenIfNecessary(
            this.battleshipsService.usersService,
            this.sessionManager,
            () => GamesService.leaveGame(
                this.session.accessToken,
                this.links.get(Rels.LEAVE_GAME) ?? throwError("Leave game link not found"),
                signal
            ),
            signal
        )
    }
}
