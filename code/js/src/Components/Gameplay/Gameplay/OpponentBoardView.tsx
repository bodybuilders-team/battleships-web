import {Cell} from "../../../Domain/games/Cell";
import {Board} from "../../../Domain/games/board/Board";
import * as React from "react";

/**
 * View that shows the opponent's board.
 *
 * @param opponentBoard the opponent's board
 * @param selectedCells the selected cells
 * @param onTileClicked the callback to be invoked when a tile is clicked
 */
export function OpponentBoardView({opponentBoard, selectedCells, onTileClicked,}: {
                                      opponentBoard: Board;
                                      selectedCells: Cell[];
                                      onTileClicked: (cell: Cell) => void;
                                  }
) {
    return (
        <></>
    );
}
