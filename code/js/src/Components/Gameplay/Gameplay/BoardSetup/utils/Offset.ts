/**
 * Offset is a class that represents an offset in 2D space.
 *
 * @property {number} x - The x offset.
 * @property {number} y - The y offset.
 */
export class Offset {
    constructor(public x: number, public y: number) {
    }

    /**
     * Returns a new offset that is the sum of this offset and the given offset.
     * @param offset The offset to add.
     * @returns {Offset} The sum of this offset and the given offset.
     */
    add(offset: Offset): Offset {
        return new Offset(this.x + offset.x, this.y + offset.y)
    }

    /**
     * Returns a new offset that is the difference of this offset and the given offset.
     * @param offset The offset to subtract.
     * @returns {Offset} The difference of this offset and the given offset.
     */
    subtract(offset: Offset): Offset {
        return new Offset(this.x - offset.x, this.y - offset.y)
    }

    public static readonly ZERO = new Offset(0, 0)
}
