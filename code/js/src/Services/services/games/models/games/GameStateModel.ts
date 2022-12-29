/**
 * The Game State Model.
 *
 * @property phase the phase of the game
 * @property phaseEndTime the time when the current phase ends
 * @property round the round of the game
 * @property turn the turn of the game
 * @property winner the winner of the game
 * @property endCause the cause of the game ending
 */
interface GameStateModel {
    phase: GamePhase
    phaseEndTime: number
    round: number | null
    turn: string | null
    winner: string | null
    endCause: string | null
}

type GamePhase = "WAITING_FOR_PLAYERS" | "DEPLOYING_FLEETS" | "IN_PROGRESS" | "FINISHED"
