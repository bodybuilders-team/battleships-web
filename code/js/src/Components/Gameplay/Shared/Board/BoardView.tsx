import Grid from "@mui/material/Grid";
import * as React from "react";
import Box from "@mui/material/Box";
import Tile, {tileSize} from "./Tile";
import {Board} from "../../../../Domain/games/board/Board";
import {Coordinate} from "../../../../Domain/games/Coordinate";

interface BoardViewProps {
    board: Board;
    enabled: boolean;
    onTileClicked?: (coordinate: Coordinate) => void;
    children?: React.ReactNode
}

/**
 * BoardView component.
 *
 * @param board the board to display
 * @param enabled whether the board is enabled or not
 * @param onTileClicked the callback to be called when a tile is clicked
 * @param children the children to be displayed on top of the board
 */
function BoardView({board, enabled, onTileClicked, children}: BoardViewProps) {

    return (
        <Box
            sx={{
                width: (board.size + 1) * tileSize,
                height: (board.size + 1) * tileSize,
                margin: 'auto',
                position: "relative",
                opacity: enabled ? 1 : 0.5
            }}>
            <Grid container columns={board.size + 1}>
                {Array.from(Array((board.size + 1) * (board.size + 1)).keys()).map((tileIndex) => {
                        const col = tileIndex % (board.size + 1);
                        const row = Math.floor(tileIndex / (board.size + 1));

                        return <Grid item key={tileIndex} xs={1} sm={1} md={1}>
                            {
                                tileIndex == 0
                                    ? <Box sx={{width: tileSize, height: tileSize}}/>
                                    : (
                                        tileIndex <= board.size
                                            ? <Box sx={{width: tileSize, height: tileSize}}>
                                                {String.fromCharCode(64 + tileIndex)}
                                            </Box>
                                            : (
                                                col === 0
                                                    ? <Box sx={{width: tileSize, height: tileSize}}>
                                                        {row}
                                                    </Box>
                                                    : <Tile onClick={() => {
                                                        if (onTileClicked)
                                                            onTileClicked(new Coordinate(col, row));
                                                    }}/>
                                            )
                                    )
                            }
                        </Grid>
                    }
                )}
            </Grid>

            {children}
        </Box>
    );
}

export default BoardView;
