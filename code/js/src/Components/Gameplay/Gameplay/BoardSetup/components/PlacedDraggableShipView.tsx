import {tileSize} from "../../../Shared/Board/Tile"
import {Ship} from "../../../../../Domain/games/ship/Ship"
import Box from "@mui/material/Box"
import * as React from "react"
import {Offset} from "../utils/Offset"
import DraggableShipView from "./DraggableShipView"

/**
 * Placed Draggable Ship View Props
 *
 * @property ship Ship
 * @property hide Whether to hide the ship
 * @property onDragStart Drag Start Callback
 * @property onDragEnd Drag End Callback
 * @property onDrag Drag Callback
 * @property onClick Click Callback
 */
interface PlacedDraggableShipViewProps {
    ship: Ship
    hide: boolean
    onDragStart: (ship: Ship, offset: Offset) => void
    onDragEnd: (ship: Ship) => void
    onDrag: (offset: Offset) => void
    onClick: () => void
}

/**
 * Placed Draggable Ship View Component
 */
function PlacedDraggableShipView(
    {
        ship,
        hide,
        onDragStart,
        onDragEnd,
        onDrag,
        onClick,
    }: PlacedDraggableShipViewProps) {


    return <Box key={ship.type.shipName} sx={{
        position: 'absolute',
        top: (ship.coordinate.row) * tileSize,
        left: (ship.coordinate.col) * tileSize,
    }}>
        <DraggableShipView
            shipType={ship.type}
            orientation={ship.orientation}
            onDragStart={(shipType, offset) => onDragStart(ship, offset)}
            onDragEnd={() => onDragEnd(ship)}
            onDrag={onDrag}
            onClick={onClick}
            opacity={hide ? 0 : 1}/>
    </Box>
}

export default PlacedDraggableShipView