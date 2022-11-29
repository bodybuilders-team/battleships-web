import * as React from "react";
import Box from "@mui/material/Box";

export const tileSize = 40;

/**
 * Tile component.
 */
function Tile() {
    return (
        <Box
            sx={{
                width: tileSize,
                height: tileSize,
                backgroundColor: '#025DA5',
                border: `1px solid lightgray`
            }}
        />
    );
}

export default Tile;
