/**
 * The Game State Model.
 *
 * @property phase the phase of the game
 * @property phaseEndTime the time when the current phase ends
 * @property round the round of the game
 * @property turn the turn of the game
 * @property winner the winner of the game
 */
interface GameStateModel {
    phase: string;
    phaseEndTime: number;
    round: number | null;
    turn: number | null;
    winner: string;
}
