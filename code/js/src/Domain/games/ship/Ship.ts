import {ShipType} from "./ShipType";
import {Orientation} from "./Orientation";
import {Coordinate} from "../Coordinate";

/**
 * A ship in the game.
 *
 * @property type the type of the ship
 * @property coordinate the coordinate of the ship
 * @property orientation the orientation of the ship
 */
export interface Ship {
    type: ShipType;
    coordinate: Coordinate;
    orientation: Orientation;
}

export class Ship {

    coordinates(): Coordinate[] {
        return getCoordinates(this.type, this.coordinate, this.orientation);
    }

    constructor(type: ShipType, coordinate: Coordinate, orientation: Orientation) {
        this.type = type;
        this.coordinate = coordinate;
        this.orientation = orientation;
    }
}

/**
 * Returns the list of coordinates occupied by a ship.
 *
 * @param shipType the type of the ship
 * @param coordinate the coordinate of the ship
 * @param orientation the orientation of the ship
 *
 * @return the list of coordinates occupied by the ship
 */
export function getCoordinates(
    shipType: ShipType,
    coordinate: Coordinate,
    orientation: Orientation
): Coordinate[] {
    const coordinates: Coordinate[] = [];
    for (let i = 0; i < shipType.size; i++) {
        if (orientation === Orientation.HORIZONTAL)
            coordinates.push(new Coordinate(coordinate.col + i, coordinate.row));
        else if (orientation === Orientation.VERTICAL)
            coordinates.push(new Coordinate(coordinate.col, coordinate.row + i));
    }
    return coordinates;
}

/**
 * Checks if the given coordinate is valid for the given ship information.
 *
 * @param coordinate the coordinate
 * @param orientation the orientation of the ship
 * @param size the size of the ship
 * @param boardSize the size of the grid
 *
 * @return true if the coordinate is valid, false otherwise
 */
export function isValidShipCoordinate(
    coordinate: Coordinate,
    orientation: Orientation,
    size: number,
    boardSize: number
): boolean {
    return (
        orientation === Orientation.HORIZONTAL &&
        ((coordinate.col + size - 1) > 1 && (coordinate.col + size - 1) < boardSize) &&
        coordinate.row > 1 && coordinate.row < boardSize
    ) || (
        orientation === Orientation.VERTICAL &&
        ((coordinate.row + size - 1) > 1 && (coordinate.row + size - 1) < boardSize) &&
        coordinate.col > 1 && coordinate.col < boardSize
    );
}
