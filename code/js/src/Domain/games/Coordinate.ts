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
    }
}
