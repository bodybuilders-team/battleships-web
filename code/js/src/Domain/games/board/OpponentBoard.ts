import {Board} from "./Board";
import {Cell} from "../Cell";

/**
 * The opponent's board.
 *
 * @param size the size of the board
 * @param grid the grid of the board
 *
 * @property fleet the fleet of the board
 */
export interface OpponentBoard extends Board {
}

export class OpponentBoard extends Board {
    constructor(boardSize: number, grid: Cell[]) {
        super(boardSize, grid);
    }
}
