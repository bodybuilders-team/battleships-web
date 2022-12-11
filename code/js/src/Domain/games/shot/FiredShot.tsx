import {Coordinate} from "../Coordinate";
import {ShotResult} from "./ShotResult";
import {Ship} from "../ship/Ship";
import {ShipType} from "../ship/ShipType";
import {throwError} from "../../../Services/utils/errorUtils";
import {Orientation} from "../ship/Orientation";

/**
 * A shot fired by a player.
 *
 * @property coordinate the coordinate of the shot
 * @property round the round in which the shot was fired
 * @property result the result of the shot
 * @property sunkShip the ship that was sunk by the shot, or null if the shot didn't sink any ship
 */
export class FiredShot {
    readonly coordinate: Coordinate
    readonly round: number
    readonly result: ShotResult
    readonly sunkShip: Ship | null

    constructor(firedShotModel: FiredShotModel, shipTypes: ReadonlyMap<ShipType, number>) {
        this.coordinate = Coordinate.fromCoordinateModel(firedShotModel.coordinate);
        this.round = firedShotModel.round;
        this.result = ShotResult.parse(firedShotModel.result.result);

        this.sunkShip = firedShotModel.sunkShip != null
            ? new Ship(
                Array.from(shipTypes.keys()).find((shipType) =>
                    shipType.shipName === firedShotModel.sunkShip?.type
                ) ?? throwError("Ship type not found"),
                Coordinate.fromCoordinateModel(firedShotModel.sunkShip?.coordinate
                    ?? throwError("Sunk ship coordinate is null")),
                Orientation.parse(firedShotModel.sunkShip?.orientation
                    ?? throwError("Sunk ship orientation is null"))
            )
            : null;
    }
}
