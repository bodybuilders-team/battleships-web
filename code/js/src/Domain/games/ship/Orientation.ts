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
    export function opposite(orientation: Orientation): Orientation {
        return orientation === Orientation.VERTICAL ?
            Orientation.HORIZONTAL : Orientation.VERTICAL
    }

    export function toOrientation(orientation: string): Orientation {
        if (orientation === "VERTICAL")
            return Orientation.VERTICAL;
        else if (orientation === "HORIZONTAL")
            return Orientation.HORIZONTAL;
        else
            throw new Error("Invalid orientation: " + orientation);
    }

    export function toString(orientation: Orientation): string {
        if (orientation === Orientation.VERTICAL)
            return "VERTICAL";
        else if (orientation === Orientation.HORIZONTAL)
            return "HORIZONTAL";
        else
            throw new Error("Invalid orientation: " + orientation);
    }
}