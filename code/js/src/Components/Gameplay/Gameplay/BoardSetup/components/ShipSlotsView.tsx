import {ShipType} from "../../../../../Domain/games/ship/ShipType"
import {Offset} from "../utils/Offset"
import Box from "@mui/material/Box"
import ShipView from "../../../Shared/Ship/ShipView"
import {Orientation} from "../../../../../Domain/games/ship/Orientation"
import * as React from "react"
import DraggableShipView from "./DraggableShipView"

/**
 * Properties of the ShipSlotsView component.
 *
 * @property shipTypes the types of the ships to display
 * @property draggingUnplaced the dragging unplaced callback
 * @property onDragStart the callback for when a ship starts to be dragged
 * @property onDragEnd the callback for when a ship stops being dragged
 * @property onDrag the callback for when a ship is being dragged
 */
interface ShipSlotsViewProps {
    shipTypes: ReadonlyMap<ShipType, number>
    draggingUnplaced: (shipType: ShipType) => boolean
    onDragStart: (shipType: ShipType, offset: Offset) => void
    onDragEnd: (shipType: ShipType) => void
    onDrag: (offset: Offset) => void
}

/**
 * Ship Slots View Component.
 */
function ShipSlotsView(
    {
        shipTypes,
        draggingUnplaced,
        onDragStart,
        onDragEnd,
        onDrag,
    }: ShipSlotsViewProps
) {
    return (
        <Box sx={{
            display: 'flex',
            flexDirection: 'row',
            justifyContent: 'space-between'
        }}>
            {
                Array.from(shipTypes.entries()).map(([shipType, count]) =>
                    <Box key={shipType.shipName}
                         sx={{
                             display: 'flex',
                             flexDirection: 'column',
                             justifyContent: 'space-between',
                         }}
                    >
                        <Box sx={{
                            display: 'flex',
                            marginTop: '10px',
                            opacity: count === 0 ? 0.5 : 1,
                        }}>
                            {
                                (count > 0)
                                    ? <DraggableShipView
                                        shipType={shipType}
                                        orientation={Orientation.VERTICAL}
                                        onDragStart={onDragStart}
                                        onDragEnd={onDragEnd}
                                        onDrag={onDrag}
                                        opacity={draggingUnplaced(shipType) ? 0.5 : 1}
                                    />
                                    : <ShipView
                                        shipType={shipType}
                                        orientation={Orientation.VERTICAL}
                                    />
                            }
                        </Box>
                        <Box sx={{marginTop: '10px'}}>{count}</Box>
                    </Box>
                )
            }
        </Box>
    )
}

export default ShipSlotsView
