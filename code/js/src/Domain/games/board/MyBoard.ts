import {Board} from "./Board";
import {Cell} from "../Cell";

/**
 * A board of the player.
 *
 * @property size the size of the board
 * @property grid the grid of the board
 *
 * @property fleet the fleet of the board
 */
export class MyBoard extends Board {
    constructor(boardSize: number, grid: Cell[]) {
        super(boardSize, grid);
    }
}
