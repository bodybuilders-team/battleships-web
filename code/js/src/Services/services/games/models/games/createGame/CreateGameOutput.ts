import {SirenEntity} from "../../../../../media/siren/SirenEntity"

/**
 * The CreateGame Output Model.
 *
 * @property gameId the id of the game
 */
interface CreateGameOutputModel {
    gameId: number
}

/**
 * The Create Game Output.
 */
export type CreateGameOutput = SirenEntity<CreateGameOutputModel>
