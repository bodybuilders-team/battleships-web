import {SirenEntity} from "../../../../../media/siren/SirenEntity";

/**
 * The Get Games Output Model.
 *
 * @property totalCount the total number of games
 */
interface GetGamesOutputModel {
    totalCount: number;
}

/**
 * The Get Games Output.
 */
export type GetGamesOutput = SirenEntity<GetGamesOutputModel>;
