import {UndeployedShipModel} from "../ship/UndeployedShipModel"

/**
 * The Deploy Fleet Input.
 *
 * @param fleet the list of ships to deploy
 */
export interface DeployFleetInput {
    fleet: UndeployedShipModel[]
}
