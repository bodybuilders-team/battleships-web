/**
 * The possible results of a shot.
 *
 * @property HIT the shot hit a ship
 * @property MISS the shot missed and hit the water
 * @property SUNK the shot sunk a ship
 */
export enum ShotResult {
    HIT,
    MISS,
    SUNK
}

export namespace ShotResult {

    /**
     * Returns the shot result from the given string.
     *
     * @param shotResult the shot result as a string
     * @returns the shot result
     */
    export function parse(shotResult: string): ShotResult {
        switch (shotResult) {
            case "HIT":
                return ShotResult.HIT
            case "MISS":
                return ShotResult.MISS
            case "SUNK":
                return ShotResult.SUNK
        }

        throw new Error("Unknown shot result")
    }
}
