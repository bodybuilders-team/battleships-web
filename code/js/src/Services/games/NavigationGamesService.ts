import {GetGamesOutput} from "./models/games/getGames/GetGamesOutput";
import {GamesService} from "./GamesService";
import {Rels} from "../utils/Rels";
import {throwError} from "../utils/errorUtils";
import {GetGameOutput, GetGameOutputModel} from "./models/games/getGame/GetGameOutput";
import {Session, SessionManager} from "../../Utils/Session";
import {MatchmakeOutput} from "./models/games/matchmake/MatchmakeOutput";
import {JoinGameOutput} from "./models/games/joinGame/JoinGameOutput";
import {SirenEntity} from "../utils/siren/SirenEntity";
import {GetGameStateOutput} from "./models/games/getGameState/GetGameStateOutput";
import NavigationBattleshipsService from "../NavigationBattleshipsService";
import { CreateGameInput } from "./models/games/createGame/CreateGameInput";
import {CreateGameOutput} from "./models/games/createGame/CreateGameOutput";

/**
 * Service to navigate through the games endpoints.
 */
export class NavigationGamesService {
    private get links(): Map<string, string> {
        return this.battleshipsService.links;
    }

    private get session(): Session {
        return this.sessionManager.session ?? throwError("Session not found");
    }

    constructor(private battleshipsService: NavigationBattleshipsService, private sessionManager: SessionManager) {
    }


    /**
     * Gets the games information.
     *
     * @return a promise with the API result of the get games request
     */
    async getGames(): Promise<GetGamesOutput> {
        if (!this.links.has(Rels.LIST_GAMES))
            await this.battleshipsService.usersService.getUserHome()

        const res = await GamesService.getGames(
            this.links.get(Rels.LIST_GAMES)
            ?? throwError("List games link not found")
        );

        res.getEmbeddedSubEntities<GetGameOutputModel>().forEach(entity => {
            const id = entity?.properties?.id ?? throwError("Game id not found");

            this.links.set(`${Rels.GAME}-${id}`, entity.getLink(Rels.SELF));
            this.links.set(`${Rels.JOIN_GAME}-${id}`, entity.getLink(Rels.JOIN_GAME));
        })

        return res
    }

    /**
     * Creates a new game.
     * @param name the name of the game
     * @param gameConfig the game configuration
     *
     * @return a promise with the API result of the create game request
     */
    async createGame(createGameInput: CreateGameInput): Promise<CreateGameOutput> {
        if (!this.links.has(Rels.CREATE_GAME))
            await this.battleshipsService.usersService.getUserHome()

        const res = await GamesService.createGame(
            this.session.accessToken,
            this.links.get(Rels.CREATE_GAME) ??
            throwError("Create game link not found"),
            createGameInput
        );

        this.links.set(Rels.GAME, res.getEmbeddedLinks(Rels.GAME)[0].href);
        this.links.set(Rels.GAME_STATE, res.getEmbeddedLinks(Rels.GAME_STATE)[0].href);

        return res;
    }

    /**
     * Matchmakes the user into a game.
     * @param gameConfig the game configuration
     *
     * @return a promise with the API result of the matchmake request
     */
    async matchmake(gameConfig: GameConfigModel): Promise<MatchmakeOutput> {
        if (!this.links.has(Rels.MATCHMAKE))
            await this.battleshipsService.usersService.getUserHome()

        const res = await GamesService.matchmake(
            this.session.accessToken,
            this.links.get(Rels.MATCHMAKE) ??
            throwError("Matchmake link not found"),
            gameConfig
        );

        this.links.set(Rels.GAME, res.getEmbeddedLinks(Rels.GAME)[0].href);
        this.links.set(Rels.GAME_STATE, res.getEmbeddedLinks(Rels.GAME_STATE)[0].href);

        return res;
    }

    /**
     * Gets the game information.
     *
     * @param gameId the id of the game
     *
     * @return a promise with the API result of the get game request
     */
    async getGame(): Promise<GetGameOutput> {
        const res = await GamesService.getGame(
            this.session.accessToken,
            this.links.get(Rels.GAME) ??
            throwError("Game link not found")
        );

        this.links.set(Rels.GAME_STATE, res.getEmbeddedLinks(Rels.GAME_STATE)[0].href);
        res.getActionLinks().forEach((value, key) => {
            this.links.set(key, value);
        })

        return res;
    }

    /**
     * Gets the game state.
     *
     * @return a promise with the API result of the get game state request
     */
    async getGameState(): Promise<GetGameStateOutput> {
        return await GamesService.getGameState(
            this.session.accessToken,
            this.links.get(Rels.GAME_STATE) ??
            throwError("Game state link not found")
        );
    }

    /**
     * Joins the user into a game.
     *
     * @param gameId the id of the game
     *
     * @return a promise with the API result of the join game request
     */
    async joinGame(): Promise<JoinGameOutput> {
        const res = await GamesService.joinGame(
            this.session.accessToken,
            this.links.get(Rels.JOIN_GAME) ??
            throwError("Join game link not found")
        );

        this.links.set(Rels.GAME, res.getEmbeddedLinks(Rels.GAME)[0].href);
        this.links.set(Rels.GAME_STATE, res.getEmbeddedLinks(Rels.GAME_STATE)[0].href);

        return res;
    }


    /**
     * Makes the user leave the game.
     *
     * @return a promise with the API result of the leave game request
     */
    async leaveGame(): Promise<SirenEntity<void>> {
        return await GamesService.leaveGame(
            this.session.accessToken,
            this.links.get(Rels.LEAVE_GAME) ??
            throwError("Leave game link not found")
        );
    }

}