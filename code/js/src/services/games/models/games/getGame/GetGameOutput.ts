import {SirenEntity} from "../../../../utils/siren/SirenEntity"

/**
 * The Get Game Output Model.
 *
 * @property id the id of the game
 * @property name the name of the game
 * @property creator the creator of the game
 * @property config the configuration of the game
 * @property state the state of the game
 * @property players the players of the game
 */
interface GetGameOutputModel {
    id: string;
    name: string;
    creator: string;
    config: GameConfigModel;
    state: GameStateModel;
    players: PlayerModel[];
}

/**
 * The Get Game Output.
 */
export type GetGameOutput = SirenEntity<GetGameOutputModel>;
