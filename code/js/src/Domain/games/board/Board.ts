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
export class Board {
    readonly size: number;
    readonly grid: ReadonlyArray<Cell>;
    readonly fleet: ReadonlyArray<Ship>;

    constructor(boardSize: number, grid: ReadonlyArray<Cell>) {
        this.size = boardSize;
        this.grid = grid;
        this.fleet = this.grid
            .filter(cell => cell instanceof ShipCell)
            .map(cell => (cell as ShipCell).ship)
            .filter((ship, index, self) => self.indexOf(ship) === index); // distinct
    }

    /**
     * Returns the cell in [coordinate].
     *
     * @param coordinate coordinate to get cell of
     *
     * @return cell in [coordinate]
     * @throws IllegalArgumentException if [coordinate] is out of bounds
     */
    getCell(coordinate: Coordinate): Cell {
        return this.grid[toIndex(coordinate, this.size)];
    }
}

export const defaultBoardSize = 10;
export const minBoardSize = 7;
export const maxBoardSize = 18;

/**
 * Generates a matrix only with water cells.
 *
 * @param size the size of the matrix
 * @return generated matrix
 */
export function generateEmptyMatrix(size: number): Cell[] {
    const grid: Cell[] = [];
    for (let i = 0; i < size * size; i++) {
        grid.push(new WaterCell(Coordinate.fromIndex(i, size), false));
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
export function generateRandomMatrix(size: number, ships: ReadonlyMap<ShipType, number>): Cell[] {
    const grid = generateEmptyMatrix(size);

    const shuffledShips: ShipType[] = Array.from(ships.entries())
        .flatMap(([shipType, count]) => {
            return Array(count).fill(shipType);
        })
        .sort(() => Math.random() - 0.5);

    shuffledShips.forEach(shipType => {
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

        ship.coordinates.forEach(coordinate => {
            grid[toIndex(coordinate, size)] = new ShipCell(coordinate, false, ship);
        });
    });

    return grid;
}
