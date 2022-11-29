import {get, getWithAuth, postWithAuth} from "../utils/fetchSiren";
import {GetGamesOutput} from "./models/games/getGames/GetGamesOutput";
import {CreateGameOutput} from "./models/games/createGame/CreateGameOutput";
import {MatchmakeOutput} from "./models/games/matchmake/MatchmakeOutput";
import {GetGameOutput} from "./models/games/getGame/GetGameOutput";
import {GetGameStateOutput} from "./models/games/getGameState/GetGameStateOutput";
import {JoinGameOutput} from "./models/games/joinGame/JoinGameOutput";

/**
 * Gets all the games.
 *
 * @param listGamesLink the link to the list games endpoint
 *
 * @return the API result of the get games request
 */
export async function getGames(listGamesLink: string): Promise<GetGamesOutput> {
    return await get(listGamesLink);
}

/**
 * Creates a new game.
 *
 * @param token the token of the user
 * @param createGameLink the link to the create game endpoint
 * @param gameConfig the game configuration
 *
 * @return the API result of the create game request
 */
export async function createGame(
    token: string,
    createGameLink: string,
    gameConfig: GameConfigModel
): Promise<CreateGameOutput> {
    return await postWithAuth(createGameLink, token, JSON.stringify(gameConfig));
}

/**
 * Matchmakes a game with a specific configuration.
 *
 * @param token the token of the user that is matchmaking
 * @param matchmakeLink the link to the matchmake endpoint
 * @param gameConfig the game configuration
 *
 * @return the API result of the matchmake request
 */
export async function matchmake(
    token: string,
    matchmakeLink: string,
    gameConfig: GameConfigModel
): Promise<MatchmakeOutput> {
    return await postWithAuth(matchmakeLink, token, JSON.stringify(gameConfig));
}

/**
 * Gets a game by id.
 *
 * @param token the user token for the authentication
 * @param gameLink the game link
 *
 * @return the result of the get game request
 */
export async function getGame(token: string, gameLink: string): Promise<GetGameOutput> {
    return await getWithAuth(gameLink, token);
}

/**
 * Gets the state of a game.
 *
 * @param token the token of the user that is matchmaking
 * @param gameStateLink the game state link
 *
 * @return the API result of the get game state request
 */
export async function getGameState(token: string, gameStateLink: string): Promise<GetGameStateOutput> {
    return await getWithAuth(gameStateLink, token);
}

/**
 * Joins a game.
 *
 * @param token the token of the user that is joining the game
 * @param joinGameLink the link to the join game endpoint
 *
 * @return the API result of the join game request
 */
export async function joinGame(token: string, joinGameLink: string): Promise<JoinGameOutput> {
    return await postWithAuth(joinGameLink, token);
}

/**
 * TODO
 * Leaves a game.
 *
 * @param token the token of the user that is leaving the game
 * @param leaveGameLink the link to the leave game endpoint
 *
 * @return the API result of the leave game request
 */
/*
export async function leaveGame(token: string, leaveGameLink: string): Promise<LeaveGameOutput> {
    return await postWithAuth(leaveGameLink, token);
}*/
