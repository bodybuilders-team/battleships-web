import {get, getWithAuth, postWithAuth} from "../../utils/fetchSiren"
import {GetGamesOutput} from "./models/games/getGames/GetGamesOutput"
import {CreateGameOutput} from "./models/games/createGame/CreateGameOutput"
import {MatchmakeOutput} from "./models/games/matchmake/MatchmakeOutput"
import {GetGameOutput} from "./models/games/getGame/GetGameOutput"
import {GetGameStateOutput} from "./models/games/getGameState/GetGameStateOutput"
import {JoinGameOutput} from "./models/games/joinGame/JoinGameOutput"
import {SirenEntity} from "../../media/siren/SirenEntity"
import {CreateGameInput} from "./models/games/createGame/CreateGameInput"
import {GamePhase} from "../../../Domain/games/game/GameState";

export namespace GamesService {

    /**
     * The parameters of the pagination.
     *
     * @property limit the limit of the pagination
     * @property offset the offset of the pagination
     */
    export interface PaginatedParams {
        limit?: number
        offset?: number
    }

    /**
     * The parameters to filter the games.
     *
     * @property username the username of the player
     * @property excludeUsername the username of the player to exclude
     * @property phases the phases where the game can be
     * @property ids the ids of the games
     */
    export interface GetGamesParams extends PaginatedParams {
        username?: string
        excludeUsername?: string
        phases?: GamePhase[]
        ids?: readonly number[]
    }

    /**
     * Gets all the games.
     *
     * @param listGamesLink the link to the list games endpoint
     * @param getGamesParams the parameters to filter the games
     * @param signal the signal to cancel the request
     *
     * @return the API result of the get games request
     */
    export async function getGames(
        listGamesLink: string,
        getGamesParams?: GetGamesParams,
        signal?: AbortSignal
    ): Promise<GetGamesOutput> {

        const searchParams = new URLSearchParams()

        if (getGamesParams) {
            for (const [key, value] of Object.entries(getGamesParams))
                searchParams.append(key, value.toString())
        }

        return await get(listGamesLink + "?" + searchParams.toString())
    }

    /**
     * Creates a new game.
     *
     * @param token the token of the user
     * @param createGameLink the link to the create game endpoint
     * @param createGameInput the game configuration
     * @param signal the signal to cancel the request
     *
     * @return the API result of the create game request
     */
    export async function createGame(
        token: string,
        createGameLink: string,
        createGameInput: CreateGameInput,
        signal?: AbortSignal
    ): Promise<CreateGameOutput> {
        return await postWithAuth(createGameLink, token, JSON.stringify(createGameInput), signal)
    }

    /**
     * Matchmakes a game with a specific configuration.
     *
     * @param token the token of the user that is matchmaking
     * @param matchmakeLink the link to the matchmake endpoint
     * @param gameConfig the game configuration
     * @param signal the signal to cancel the request
     *
     * @return the API result of the matchmake request
     */
    export async function matchmake(
        token: string,
        matchmakeLink: string,
        gameConfig: GameConfigModel,
        signal?: AbortSignal
    ): Promise<MatchmakeOutput> {
        return await postWithAuth(matchmakeLink, token, JSON.stringify(gameConfig), signal)
    }

    /**
     * Gets a game by id.
     *
     * @param token the user token for the authentication
     * @param gameLink the game link
     * @param signal the signal to cancel the request
     *
     * @return the result of the get game request
     */
    export async function getGame(
        token: string,
        gameLink: string,
        signal?: AbortSignal
    ): Promise<GetGameOutput> {
        return await getWithAuth(gameLink, token, signal)
    }

    /**
     * Gets the state of a game.
     *
     * @param token the token of the user that is matchmaking
     * @param gameStateLink the game state link
     * @param signal the signal to cancel the request
     *
     * @return the API result of the get game state request
     */
    export async function getGameState(
        token: string,
        gameStateLink: string,
        signal?: AbortSignal
    ): Promise<GetGameStateOutput> {
        return await getWithAuth(gameStateLink, token, signal)
    }

    /**
     * Joins a game.
     *
     * @param token the token of the user that is joining the game
     * @param joinGameLink the link to the join game endpoint
     * @param signal the signal to cancel the request
     *
     * @return the API result of the join game request
     */
    export async function joinGame(
        token: string,
        joinGameLink: string,
        signal?: AbortSignal
    ): Promise<JoinGameOutput> {
        return await postWithAuth(joinGameLink, token, undefined, signal)
    }

    /**
     * Leaves a game.
     *
     * @param token the token of the user that is leaving the game
     * @param leaveGameLink the link to the leave game endpoint
     * @param signal the signal to cancel the request
     *
     * @return the API result of the leave game request
     */
    export async function leaveGame(
        token: string,
        leaveGameLink: string,
        signal?: AbortSignal
    ): Promise<SirenEntity<void>> {
        return await postWithAuth(leaveGameLink, token, undefined, signal)
    }
}
