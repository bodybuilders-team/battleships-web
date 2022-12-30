import {GameStateModel} from "../../../Services/services/games/models/games/GameStateModel";

/**
 * The Game State.
 *
 * @property phase the phase of the game
 * @property phaseEndTime the time when the current phase ends
 * @property round the round of the game
 * @property turn the turn of the game
 * @property winner the winner of the game
 * @property endCause the cause of the game ending
 */
export class GameState {
    readonly phase: GamePhase
    readonly phaseEndTime: number
    readonly round: number | null;
    readonly turn: string | null;
    readonly winner: string | null;
    readonly endCause: EndGameCause | null;

    constructor(gameStateModel: GameStateModel) {
        this.phase = gameStateModel.phase;
        this.phaseEndTime = gameStateModel.phaseEndTime;
        this.round = gameStateModel.round;
        this.turn = gameStateModel.turn;
        this.winner = gameStateModel.winner;
        this.endCause = gameStateModel.endCause;
    }
}

/**
 * The Game Phase.
 */
export type GamePhase = "WAITING_FOR_PLAYERS" | "DEPLOYING_FLEETS" | "IN_PROGRESS" | "FINISHED"

/**
 * The End Game Cause.
 */
export type EndGameCause = `DESTRUCTION` | `RESIGNATION` | `TIMEOUT`
