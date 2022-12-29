/**
 * Coordinate of each board cell.
 *
 * @property col char in range [maxColsRange]
 * @property row int in range [maxRowsRange]
 */
export class Coordinate {
    col: number
    row: number

    constructor(col: number, row: number) {
        this.col = col
        this.row = row

        if (col < 1 || row < 1)
            throw new Error("Coordinates must be > 1")
    }

    /**
     * Converts a CoordinateModel to a Coordinate.
     *
     * @return the Coordinate
     */
    static fromCoordinateModel(model: CoordinateModel): Coordinate {
        return new Coordinate(model.col.charCodeAt(0) - "A".charCodeAt(0) + 1, model.row)
    }

    /**
     * Returns a coordinate given an index and a board size.
     *
     * @param index the index
     * @param size the board size
     */
    static fromIndex(index: number, size: number): Coordinate {
        const row = Math.floor(index / size) + 1
        const col = index % size + 1

        return new Coordinate(col, row)
    }

    /**
     * Converts a Coordinate to a CoordinateModel.
     *
     * @return the CoordinateModel
     */
    toCoordinateModel(): CoordinateModel {
        return {
            col: String.fromCharCode('A'.charCodeAt(0) + this.col - 1),
            row: this.row
        }
    }

    static isValid(col: number, row: number, size: number): boolean {
        return col >= 1 && col <= size && row >= 1 && row <= size
    }

    equals(other: Coordinate): boolean {
        return this.col === other.col && this.row === other.row
    }
}
