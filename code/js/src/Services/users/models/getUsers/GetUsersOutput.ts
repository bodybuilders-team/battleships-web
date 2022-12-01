import {SirenEntity} from "../../../utils/siren/SirenEntity";

/**
 * The Get Users Output Model.
 *
 * @property totalCount the total number of users
 */
interface GetUsersOutputModel {
    totalCount: number;
}

/**
 * The Get Users Output.
 */
export type GetUsersOutput = SirenEntity<GetUsersOutputModel>;