import {Board} from "./Board";
import {Cell, ShipCell} from "../Cell";
import {Ship} from "../ship/Ship";

/**
 * A board in the game.
 *
 * @property size the size of the board
 * @property grid the matrix of cells
 *
 * @property fleet the fleet of ships
 */
export interface ConfigurableBoard extends Board {
    fleet: Ship[];
}

export class ConfigurableBoard extends Board {
    constructor(boardSize: number, grid: Cell[]) {
        super(boardSize, grid);
        this.fleet = grid
            .filter(cell => cell instanceof ShipCell)
            .map(cell => (cell as ShipCell).ship)
            .filter((ship, index, self) => self.indexOf(ship) === index); // distinct
    }
}

