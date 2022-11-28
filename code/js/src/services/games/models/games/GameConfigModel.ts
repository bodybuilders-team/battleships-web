/**
 * The Game Config Model.
 *
 * @property gridSize the size of the grid
 * @property maxTimePerRound the maximum time per shot
 * @property shotsPerRound the number of shots per round
 * @property maxTimeForLayoutPhase the maximum time for the layout phase
 * @property shipTypes the ship types allowed in the game
 */
interface GameConfigModel {
    gridSize: number;
    maxTimePerRound: number;
    shotsPerRound: number;
    maxTimeForLayoutPhase: number;
    shipTypes: ShipTypeModel[];
}
