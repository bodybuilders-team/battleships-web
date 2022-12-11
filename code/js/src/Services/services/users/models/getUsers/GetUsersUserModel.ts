/**
 * A Get Users User Model.
 *
 * @property username the username of the user
 * @property email the email of the user
 * @property points the points of the user
 * @property numberOfGamesPlayed the number of games played by the user
 */
export interface GetUsersUserModel {
    username: string
    email: string
    points: number
    numberOfGamesPlayed: number
}
