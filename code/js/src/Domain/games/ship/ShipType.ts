/**
 * The ship class in the game.
 *
 * @property size the size of the ship
 * @property shipName the name of the ship
 * @property points the points that the ship is worth
 */
export interface ShipType {
    size: number;
    shipName: string;
    points: number;
}

export class ShipType {
    constructor(size: number, shipName: string, points: number) {
        this.size = size;
        this.shipName = shipName;
        this.points = points;
    }
}
