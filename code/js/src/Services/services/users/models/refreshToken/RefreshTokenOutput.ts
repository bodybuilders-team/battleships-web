import {SirenEntity} from "../../../../media/siren/SirenEntity";

/**
 * The Refresh Token Output Model.
 *
 * @property accessToken the access token
 * @property refreshToken the refresh token
 */
interface RefreshTokenOutputModel {
    accessToken: string;
    refreshToken: string;
}

export type RefreshTokenOutput = SirenEntity<RefreshTokenOutputModel>;