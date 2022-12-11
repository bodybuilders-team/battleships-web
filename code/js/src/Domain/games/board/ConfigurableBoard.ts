import {Board, toIndex} from "./Board";
import {Cell, ShipCell, WaterCell} from "../Cell";
import {Ship} from "../ship/Ship";

/**
 * A board in the game.
 *
 * @property size the size of the board
 * @property grid the matrix of cells
 *
 * @property fleet the fleet of ships
 */
export class ConfigurableBoard extends Board {
    constructor(boardSize: number, grid: Cell[]) {
        super(boardSize, grid);
    }


    canPlaceShip(ship: Ship): boolean {
        return ship.coordinates.every(coordinate => {
            const cell = this.getCell(coordinate);
            return cell instanceof WaterCell;
        });
    }

    placeShip(ship: Ship): ConfigurableBoard {
        const newGrid = this.grid.slice();
        ship.coordinates.forEach(coordinate => {
            const index = toIndex(coordinate, this.size);
            newGrid[index] = new ShipCell(coordinate, false, ship);
        });
        return new ConfigurableBoard(this.size, newGrid);
    }

    removeShip(ship: Ship): ConfigurableBoard {
        const newGrid = this.grid.slice();
        ship.coordinates.forEach(coordinate => {
            const index = toIndex(coordinate, this.size);
            newGrid[index] = new WaterCell(coordinate, false);
        });
        return new ConfigurableBoard(this.size, newGrid);
    }
}

