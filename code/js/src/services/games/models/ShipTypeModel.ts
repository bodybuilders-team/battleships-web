/**
 * The Ship Type Model.
 *
 * @property shipName the name of the ship
 * @property size the size of the ship
 * @property quantity the quantity of ships of this type
 * @property points the points that the ship is worth
 */
interface ShipTypeModel {
    shipName: string;
    size: number;
    quantity: number;
    points: number;
}
