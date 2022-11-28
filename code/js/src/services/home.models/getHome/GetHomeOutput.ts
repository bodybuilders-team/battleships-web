import {SirenEntity} from "../../utils/siren/SirenEntity";
import {AuthorModel} from "../AuthorModel";
import {VCRepositoryModel} from "../VCRepositoryModel";

/**
 * The Get Home Output Model.
 *
 * @property title the title of the application
 * @property version the version of the application
 * @property description the description of the application
 * @property authors the authors of the application
 * @property repository the repository of the application
 */
interface GetHomeOutputModel {
    title: string;
    version: string;
    description: string;
    authors: AuthorModel[];
    repository: VCRepositoryModel;
}

/**
 * The Get Home Output.
 */
export type GetHomeOutput = SirenEntity<GetHomeOutputModel>

