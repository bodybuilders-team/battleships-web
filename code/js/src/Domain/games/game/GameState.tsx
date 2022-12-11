/**
 * The Game State.
 *
 * @property phase the phase of the game
 * @property phaseEndTime the time when the current phase ends
 * @property round the round of the game
 * @property turn the turn of the game
 * @property winner the winner of the game
 */
export class GameState {
    readonly phase: "WAITING_FOR_PLAYERS" | "DEPLOYING_FLEETS" | "IN_PROGRESS" | "FINISHED";
    readonly phaseEndTime: number;
    readonly round: number | null;
    readonly turn: string | null;
    readonly winner: string | null;

    constructor(gameStateModel: GameStateModel) {
        this.phase = gameStateModel.phase;
        this.phaseEndTime = gameStateModel.phaseEndTime;
        this.round = gameStateModel.round;
        this.turn = gameStateModel.turn;
        this.winner = gameStateModel.winner;
    }
}