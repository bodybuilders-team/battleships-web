/**
 * The ship's orientation.
 *
 * @property VERTICAL the vertical orientation
 * @property HORIZONTAL the horizontal orientation
 */
export enum Orientation {
    VERTICAL,
    HORIZONTAL
}

export namespace Orientation {
    /**
     * Returns the opposite orientation.
     *
     * @return the opposite orientation
     */
    export function opposite(orientation: Orientation): Orientation {
        return orientation === Orientation.VERTICAL
            ? Orientation.HORIZONTAL
            : Orientation.VERTICAL
    }

    /**
     * Parses the orientation from a string.
     *
     * @param orientation the orientation as a string
     * @return the orientation
     */
    export function parse(orientation: string): Orientation {
        if (orientation === "VERTICAL")
            return Orientation.VERTICAL
        else if (orientation === "HORIZONTAL")
            return Orientation.HORIZONTAL
        else
            throw new Error("Invalid orientation: " + orientation)
    }

    export function toString(orientation: Orientation): string {
        if (orientation === Orientation.VERTICAL)
            return "VERTICAL"
        else if (orientation === Orientation.HORIZONTAL)
            return "HORIZONTAL"
        else
            throw new Error("Invalid orientation: " + orientation)
    }
}
