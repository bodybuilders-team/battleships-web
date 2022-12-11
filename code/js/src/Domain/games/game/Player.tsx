/**
 * A player.
 *
 * @property username the username of the player
 * @property points the points of the player
 */
export class Player {
    readonly username: string;
    readonly points: number;

    constructor(playerModel: PlayerModel) {
        this.username = playerModel.username;
        this.points = playerModel.points;
    }

    /**
     * Converts the Player to a PlayerModel.
     *
     * @returns the PlayerModel
     */
    toPlayerModel(): PlayerModel {
        return {
            username: this.username,
            points: this.points
        }
    }
}
