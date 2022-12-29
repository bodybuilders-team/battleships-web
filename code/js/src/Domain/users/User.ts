/**
 * A user of the application.
 *
 * @property username the user's username
 * @property email the user's email
 * @property points the user's points
 * @property numberOfGamesPlayed the number of games played by the user
 */
export interface User {
    username: string
    email: string
    points: number
    numberOfGamesPlayed: number
}

export class User {
    constructor(username: string, email: string, points: number, numberOfGamesPlayed: number) {
        this.username = username
        this.email = email
        this.points = points
        this.numberOfGamesPlayed = numberOfGamesPlayed
    }
}
