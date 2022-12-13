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

    /**
     * Checks if it is possible to place a ship in its coordinates.
     *
     * @param ship ship to check
     * @return true if it is possible to place the ship in its coordinates, false otherwise
     */
    canPlaceShip(ship: Ship): boolean {
        return ship.coordinates.every(coordinate => {
            const cell = this.getCell(coordinate);
            return cell instanceof WaterCell;
        });
    }

    /**
     * Places a ship in the board.
     *
     * @param ship the ship to place
     * @return the new board with the ship placed
     */
    placeShip(ship: Ship): ConfigurableBoard {
        const newGrid = this.grid.slice();

        ship.coordinates.forEach(coordinate => {
            const index = toIndex(coordinate, this.size);
            newGrid[index] = new ShipCell(coordinate, false, ship);
        });

        return new ConfigurableBoard(this.size, newGrid);
    }

    /**
     * Removes a ship from the board.
     *
     * @param ship the ship to place
     * @return the new board with the ship placed
     */
    removeShip(ship: Ship): ConfigurableBoard {
        const newGrid = this.grid.slice();

        ship.coordinates.forEach(coordinate => {
            const index = toIndex(coordinate, this.size);
            newGrid[index] = new WaterCell(coordinate, false);
        });

        return new ConfigurableBoard(this.size, newGrid);
    }
}
