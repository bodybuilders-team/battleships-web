import {Board, generateEmptyMatrix} from "./Board";
import {Cell, ShipCell, UnknownShipCell, WaterCell} from "../Cell";
import {throwError} from "../../../Services/utils/errorUtils";
import {ShotResult} from "../shot/ShotResult";
import {FiredShot} from "../shot/FiredShot";

/**
 * The opponent's board.
 *
 * @param size the size of the board
 * @param grid the grid of the board
 *
 * @property fleet the fleet of the board
 */
export class OpponentBoard extends Board {
    constructor(boardSize: number, grid: Cell[] = generateEmptyMatrix(boardSize)) {
        super(boardSize, grid);
    }

    /**
     * Shoots the [firedShots].
     * If the cell is already hit, the attack is invalid.
     * Otherwise, the cell becomes hit.
     *
     * @param firedShots coordinates to attack
     *
     * @return the board after the attack
     */
    updateWith(firedShots: FiredShot[]): OpponentBoard {
        const sunkCoordinates = new Map();
        firedShots
            .map(shot => shot.sunkShip)
            .filter(ship => ship != null)
            .forEach(ship => sunkCoordinates.set(ship?.coordinates ?? throwError("Ship should not be null"), ship));

        return new OpponentBoard(this.size, this.grid.map(cell => {
            const firedShot = firedShots.find(shot => shot.coordinate.equals(cell.coordinate));
            if (firedShot == null)
                return cell;

            if (cell.wasHit)
                throw Error(`Cell at ${cell.coordinate} was already hit`);

            if (!(cell instanceof WaterCell))
                throw Error("Opponent cell can only be not hit if it is water cell");

            const sunkShip = sunkCoordinates.get(cell.coordinate);
            if (sunkShip != null)
                return new ShipCell(cell.coordinate, true, sunkShip);

            if (firedShot.result == ShotResult.HIT)
                return new UnknownShipCell(cell.coordinate, true);

            return new WaterCell(cell.coordinate, true);
        }));
    }
}
