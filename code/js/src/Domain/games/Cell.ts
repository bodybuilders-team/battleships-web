/**
 * A cell in the board.
 *
 * @property coordinate the coordinate of the cell
 * @property wasHit if the cell was hit
 */
import {Coordinate} from "./Coordinate";
import {Ship} from "./ship/Ship";

export interface Cell {
    coordinate: Coordinate;
    wasHit: boolean;
}

export class Cell {
    constructor(coordinate: Coordinate) {
        this.coordinate = coordinate;
        this.wasHit = false;
    }
}

/**
 * An empty cell.
 */
export interface WaterCell extends Cell {
}

export class WaterCell extends Cell {
    constructor(coordinate: Coordinate) {
        super(coordinate);
    }
}

/**
 * A cell that contains a ship that is sunk.
 *
 * @property ship the ship that is in this cell
 */
export interface ShipCell extends Cell {
    ship: Ship;
}

export class ShipCell extends Cell {
    constructor(coordinate: Coordinate, ship: Ship) {
        super(coordinate);
        this.ship = ship;
    }
}

/**
 * A cell that contains a ship that is not sunk.
 * It does not contain the ship itself, since it is not sunk.
 */
export interface UnknownShipCell extends Cell {
}

export class UnknownShipCell extends Cell {
    constructor(coordinate: Coordinate) {
        super(coordinate);
    }
}
