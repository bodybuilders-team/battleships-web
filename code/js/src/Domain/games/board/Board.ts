import {Cell, ShipCell, WaterCell} from "../Cell";
import {Coordinate} from "../Coordinate";
import {ShipType} from "../ship/ShipType";
import {Orientation} from "../ship/Orientation";
import {getCoordinates, isValidShipCoordinate, Ship} from "../ship/Ship";

/**
 * A board in the game.
 *
 * @property size the size of the board
 * @property grid the grid of cells
 */
export interface Board {
    size: number;
    grid: Cell[];
}

export class Board {
    constructor(boardSize: number, grid: Cell[]) {
        this.size = boardSize;
        this.grid = grid;
    }
}


/**
 * Generates a matrix only with water cells.
 *
 * @param size the size of the matrix
 * @return generated matrix
 */
export function generateEmptyMatrix(size: number): Cell[] {
    const grid: Cell[] = [];
    for (let i = 0; i < size; i++) {
        for (let j = 0; j < size; j++) {
            grid.push(new WaterCell(new Coordinate(i, j)));
        }
    }
    return grid;
}

/**
 * Returns the matrix index obtained from the coordinate.
 *
 * @param coordinate the coordinate
 * @param size the size of the matrix
 *
 * @return the matrix index obtained from the coordinate
 */
export function toIndex(coordinate: Coordinate, size: number): number {
    return (coordinate.row - 1) * size + (coordinate.col - 1);
}

/**
 * Generates a matrix of cells with the ships placed randomly.
 *
 * @param size the size of the matrix
 * @param ships the ships to place in the matrix
 *
 * @return matrix of [size] with the [ships] placed randomly
 */
export function generateRandomMatrix(size: number, ships: ShipType[]): Cell[] {
    const grid = generateEmptyMatrix(size);

    ships.forEach(shipType => {
        const possibleShips = grid
            .filter(cell => cell instanceof WaterCell)
            .flatMap(cell => {
                return [Orientation.HORIZONTAL, Orientation.VERTICAL]
                    .filter(orientation => isValidShipCoordinate(cell.coordinate, orientation, shipType.size, size))
                    .flatMap(orientation => {
                        const coordinates = getCoordinates(shipType, cell.coordinate, orientation);

                        if (
                            coordinates.every(coordinate => {
                                    return grid[toIndex(coordinate, size)] instanceof WaterCell;
                                }
                            )
                        )
                            return [new Ship(shipType, coordinates[0], orientation)];
                        else
                            return [];
                    });
            });

        const randomIndex = Math.floor(Math.random() * possibleShips.length);
        const ship = possibleShips[randomIndex];

        ship.coordinates().forEach(coordinate => {
            grid[toIndex(coordinate, size)] = new ShipCell(coordinate, ship);
        });
    });

    return grid;
}
