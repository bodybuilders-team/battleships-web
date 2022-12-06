import * as React from "react";
import Box from "@mui/material/Box";

export const tileSize = 40;

interface TileProps {
    onClick?: () => void;
}

/**
 * Tile component.
 */
function Tile({onClick}: TileProps) {
    return (
        <Box
            sx={{
                width: tileSize,
                height: tileSize,
                backgroundColor: '#025DA5',
                border: `1px solid lightgray`
            }}
            onClick={onClick}
        />
    );
}

export default Tile;
