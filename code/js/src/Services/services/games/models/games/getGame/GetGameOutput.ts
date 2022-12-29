import {SirenEntity} from "../../../../../media/siren/SirenEntity"

/**
 * The Get Game Output Model.
 *
 * @property name the name of the game
 * @property creator the creator of the game
 * @property config the configuration of the game
 * @property state the state of the game
 * @property players the players of the game
 */
export interface GetGameOutputModel {
    id: number
    name: string
    creator: string
    config: GameConfigModel
    state: GameStateModel
    players: PlayerModel[]
}

/**
 * The Get Game Output.
 */
export type GetGameOutput = SirenEntity<GetGameOutputModel>
