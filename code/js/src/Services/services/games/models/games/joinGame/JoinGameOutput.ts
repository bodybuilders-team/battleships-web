import {SirenEntity} from "../../../../../media/siren/SirenEntity";

/**
 * The Join Game Output Model.
 *
 * @property gameId the id of the joined game
 */
interface JoinGameOutputModel {
    gameId: string;
}

/**
 * The Join Game Output.
 */
export type JoinGameOutput = SirenEntity<JoinGameOutputModel>;
