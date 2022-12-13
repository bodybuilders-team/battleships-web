import {GameConfig} from "./GameConfig";
import {GameState} from "./GameState";
import {GetGameOutputModel} from "../../../Services/services/games/models/games/getGame/GetGameOutput";
import {Player} from "./Player";

export class Game {
    readonly id: string
    readonly name: string;
    readonly creator: string;
    readonly config: GameConfig;
    readonly state: GameState;
    readonly players: ReadonlyArray<Player>

    constructor(gameModel: GetGameOutputModel) {
        this.id = gameModel.id;
        this.name = gameModel.name;
        this.creator = gameModel.creator;
        this.config = new GameConfig(gameModel.config);
        this.state = new GameState(gameModel.state);
        this.players = gameModel.players.map(player => new Player(player));
    }

    static toGameModel(game: Game): GetGameOutputModel {
        return {
            id: game.id,
            name: game.name,
            creator: game.creator,
            config: game.config.toGameConfigModel(),
            state: {
                phase: game.state.phase,
                phaseEndTime: game.state.phaseEndTime,
                round: game.state.round,
                turn: game.state.turn,
                winner: game.state.winner
            },
            players: game.players.map(player => player.toPlayerModel())
        }
    }

    getPlayer(username: string): Player | undefined {
        return this.players.find(player => player.username === username);
    }

    getOpponent(username: string): Player | undefined {
        return this.players.find(player => player.username !== this.creator)
    }
}
