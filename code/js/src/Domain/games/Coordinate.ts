/**
 * Coordinate of each board cell.«
 */
export class Coordinate {
    col: number;
    row: number;

    constructor(col: number, row: number) {
        this.col = col;
        this.row = row;
        if (col < 1 || row < 1) {
            throw new Error("Coordinates must be > 1");
        }
    }

    static fromCoordinateModel(model: CoordinateModel): Coordinate {
        return new Coordinate(model.col.charCodeAt(0) - "A".charCodeAt(0) + 1, model.row);
    }


    toCoordinateModel(): CoordinateModel {
        return {
            col: String.fromCharCode('A'.charCodeAt(0) + this.col - 1),
            row: this.row
        };
    }

    static fromIndex(index: number, size: number): Coordinate {
        const row = Math.floor(index / size) + 1;
        const col = index % size + 1;
        return new Coordinate(col, row);
    }

    equals(other
               :
               Coordinate
    ):
        boolean {
        return this.col === other.col && this.row === other.row;
    }
}