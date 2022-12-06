/**
 * Coordinate of each board cell.«
 */
export interface Coordinate {
    col: number;
    row: number;
}

export class Coordinate {
    constructor(col: number, row: number) {
        this.col = col;
        this.row = row;
        if (col < 1 || row < 1) {
            throw new Error("Coordinates must be > 1");
        }
    }
}
