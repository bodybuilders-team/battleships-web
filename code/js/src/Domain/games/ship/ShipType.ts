/**
 * The ship class in the game.
 *
 * @property size the size of the ship
 * @property shipName the name of the ship
 * @property points the points that the ship is worth
 */
export class ShipType {
    size: number;
    shipName: string;
    points: number;

    constructor(size: number, shipName: string, points: number) {
        this.size = size;
        this.shipName = shipName;
        this.points = points;
    }
}

export const defaultShipTypes: Map<ShipType, number> = new Map<ShipType, number>([
    [new ShipType(5, "Carrier", 50), 1],
    [new ShipType(4, "Battleship", 40), 1],
    [new ShipType(3, "Cruiser", 30), 3],
    [new ShipType(3, "Submarine", 30), 2],
    [new ShipType(2, "Destroyer", 20), 1]
]);
