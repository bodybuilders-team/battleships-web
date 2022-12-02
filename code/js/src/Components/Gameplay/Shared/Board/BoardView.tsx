import Grid from "@mui/material/Grid";
import * as React from "react";
import Box from "@mui/material/Box";
import Tile, {tileSize} from "./Tile";
import {Board} from "../../../../Domain/games/board/Board";


/**
 * BoardView component.
 *
 * @param board the board to display
 */
function BoardView(board: Board) {
    return (
        <Box
            sx={{
                width: (board.size + 1) * tileSize,
                height: (board.size + 1) * tileSize,
                margin: 'auto',
            }}>
            <Grid container columns={board.size + 1}>
                {Array.from(Array((board.size + 1) * (board.size + 1)).keys()).map((tileIndex) => (
                    <Grid item key={tileIndex} xs={1} sm={1} md={1}>
                        {
                            tileIndex == 0
                                ? <Box sx={{width: tileSize, height: tileSize}}/>
                                : (
                                    tileIndex <= board.size
                                        ? <Box sx={{width: tileSize, height: tileSize}}>
                                            {String.fromCharCode(64 + tileIndex)}
                                        </Box>
                                        : (
                                            tileIndex % (board.size + 1) === 0
                                                ? <Box sx={{width: tileSize, height: tileSize}}>
                                                    {tileIndex / (board.size + 1)}
                                                </Box>
                                                : <Tile/>
                                        )
                                )
                        }
                    </Grid>
                ))}
            </Grid>
        </Box>
    );
}

export default BoardView;
