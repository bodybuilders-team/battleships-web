import {SirenEntity} from "../../../../utils/siren/SirenEntity";

/**
 * The Matchmake Output Model.
 *
 * @property wasCreated true if the game was created, false if it was joined
 */
interface MatchmakeOutputModel {
    wasCreated: boolean;
}

/**
 * The Matchmake Output.
 */
export type MatchmakeOutput = SirenEntity<MatchmakeOutputModel>;
