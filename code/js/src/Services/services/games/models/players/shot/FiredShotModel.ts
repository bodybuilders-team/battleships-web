/**
 * The Fired Shot Model.
 *
 * @property coordinate the coordinate of the shot
 * @property round the round in which the shot was made
 * @property result the result of the shot
 * @property sunkShip the sunk ship, if any
 */
interface FiredShotModel {
    coordinate: CoordinateModel
    round: number
    result: ShotResultModel
    sunkShip?: DeployedShipModel
}
