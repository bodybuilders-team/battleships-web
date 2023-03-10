/**
 * The Undeployed Ship Model.
 *
 * @property type the ship type
 * @property coordinate the position
 * @property orientation the orientation
 */
export interface UndeployedShipModel {
    type: string
    coordinate: CoordinateModel
    orientation: string
}
