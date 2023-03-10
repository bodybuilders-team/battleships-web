/**
 * The Deployed Ship Model.
 *
 * @property type the ship type
 * @property coordinate the position
 * @property orientation the orientation
 */
interface DeployedShipModel {
    type: string
    coordinate: CoordinateModel
    orientation: string
}
