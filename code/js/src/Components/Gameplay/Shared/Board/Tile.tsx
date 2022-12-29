import * as React from "react"
import Box from "@mui/material/Box"

export const tileSize = 40

/**
 * Properties for the Tile component.
 *
 * @property onClick the callback to be called when the tile is clicked
 */
interface TileProps {
    onClick?: () => void
}

/**
 * Tile component.
 */
export default function Tile({onClick}: TileProps) {
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
    )
}
