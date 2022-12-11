import {Board, generateEmptyMatrix, toIndex} from "./Board";
import {Cell, ShipCell, WaterCell} from "../Cell";
import {Ship} from "../ship/Ship";
import {Coordinate} from "../Coordinate";
import "../../../Utils/arrayExtensions"

/**
 * A board of the player.
 *
 * @property size the size of the board
 * @property grid the grid of the board
 *
 * @property fleet the fleet of the board
 */
export class MyBoard extends Board {
    constructor(boardSize: number, grid: ReadonlyArray<Cell>) {
        super(boardSize, grid);
    }

    /**
     * Creates a new instance of [MyBoard] with the given size and fleet.
     *
     * @param boardSize the size of the board
     * @param fleet the initial fleet of the board
     *
     * @return instance of [MyBoard]
     */
    static fromFleet(boardSize: number, fleet: ReadonlyArray<Ship>): MyBoard {
        const grid = generateEmptyMatrix(boardSize);

        fleet.forEach(ship =>
            ship.coordinates.forEach(coordinate =>
                grid[toIndex(coordinate, boardSize)] = new ShipCell(coordinate, false, ship)
            )
        );

        return new MyBoard(boardSize, grid);
    }

    /**
     * Shoots the [firedCoordinates].
     * If the cell is already hit, the attack is invalid.
     * Otherwise, the cell becomes hit.
     *
     * @param firedCoordinates coordinates to attack
     *
     * @return the board after the attack
     */
    shoot(firedCoordinates: Coordinate[]) {
        return new MyBoard(
            this.size,
            this.grid.replaceIf((cell) =>
                    firedCoordinates.find(coordinate => coordinate.equals(cell.coordinate)) !== undefined,
                (cell) => {
                    if (cell.wasHit)
                        throw Error(`Cell at ${cell.coordinate} was already hit`);

                    if (cell instanceof ShipCell)
                        return new ShipCell(cell.coordinate, true, cell.ship);
                    else if (cell instanceof WaterCell)
                        return new WaterCell(cell.coordinate, true);

                    throw Error("UnknownShipCell should not be present in MyBoard");
                }
            )
        )
    }
}
