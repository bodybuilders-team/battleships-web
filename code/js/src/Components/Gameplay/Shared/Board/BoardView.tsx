import Grid from "@mui/material/Grid";
import * as React from "react";
import Box from "@mui/material/Box";
import Tile, {tileSize} from "./Tile";
import {Board} from "../../../../Domain/games/board/Board";


/**
 * BoardView component.
 * @param board the board to display
 */
function BoardView(board: Board) {
    return (
        <Box
            sx={{
                width: board.size * tileSize,
                height: board.size * tileSize,
                margin: 'auto',
            }}>
            <Grid container columns={board.size}>
                {Array.from(Array(board.size * board.size).keys()).map((tileIndex) => (
                    <Grid item key={tileIndex} xs={1} sm={1} md={1}>
                        <Tile/>
                    </Grid>
                ))}
            </Grid>
        </Box>
    );
}

export default BoardView;
