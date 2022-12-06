import {SirenEntity} from "../../../media/siren/SirenEntity";

/**
 * The Authentication Output Model.
 *
 * @property accessToken the access token
 * @property refreshToken the refresh token
 */
interface AuthenticationOutputModel {
    accessToken: string;
    refreshToken: string;
}

/**
 * The Authentication Output.
 */
export type AuthenticationOutput = SirenEntity<AuthenticationOutputModel>;
