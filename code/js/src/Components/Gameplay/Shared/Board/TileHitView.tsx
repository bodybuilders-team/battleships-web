import {tileSize} from "./Tile"
import * as React from "react"

/**
 * Properties for the TileHitView component.
 */
interface TileHitViewProps {
    hitShip: boolean
}

/**
 * TileHitView component.
 */
export default function TileHitView({hitShip}: TileHitViewProps) {
    const color = hitShip ? "red" : "grey"
    return (
        <svg width={tileSize} height={tileSize}>
            <line x1={0} y1={0} x2={tileSize} y2={tileSize} stroke={color} strokeWidth={2}/>
            <line x1={0} y1={tileSize} x2={tileSize} y2={0} stroke={color} strokeWidth={2}/>
        </svg>
    )
}
