import {SirenEntity} from "../../../../utils/siren/SirenEntity";

/**
 * The Deploy Fleet Output Model.
 *
 * @property successfullyDeployed true if the fleet was successfully deployed, false otherwise
 */
interface DeployFleetOutputModel {
    successfullyDeployed: boolean;
}

/**
 * The Deploy Fleet Output.
 */
export type DeployFleetOutput = SirenEntity<DeployFleetOutputModel>;
