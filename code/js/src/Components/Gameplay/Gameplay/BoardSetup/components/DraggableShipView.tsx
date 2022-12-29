import {ShipType} from "../../../../../Domain/games/ship/ShipType"
import {Offset} from "../utils/Offset"
import {DraggableCore} from "react-draggable"
import {getPosition} from "../utils/getPosition"
import ShipView from "../../../Shared/Ship/ShipView"
import {Orientation} from "../../../../../Domain/games/ship/Orientation"
import * as React from "react"

/**
 * Properties of the DraggableShipView component.
 *
 * @property shipType the type of the ship to display
 * @property orientation the orientation of the ship
 * @property onDragStart the callback to call when the ship is dragged
 * @property onDragEnd the callback to call when the ship stops being dragged
 * @property onDrag the callback to call when the ship is being dragged
 * @property onClick the callback to call when the ship is clicked
 * @property opacity the opacity of the ship
 */
interface DraggableShipViewProps {
    shipType: ShipType
    orientation: Orientation
    onDragStart: (shipType: ShipType, offset: Offset) => void
    onDragEnd: (shipType: ShipType) => void
    onDrag: (offset: Offset) => void
    onClick?: () => void
    opacity: number
}

/**
 * Draggable Ship View Component
 */
function DraggableShipView(
    {
        shipType,
        orientation,
        onDragStart,
        onDragEnd,
        onDrag,
        onClick,
        opacity
    }: DraggableShipViewProps
) {
    const [dragging, setDragging] = React.useState(false)

    return (
        <DraggableCore
            onStart={(event, data) => {
                const position = getPosition(event.currentTarget as HTMLElement)
                onDragStart(shipType, new Offset(position.left, position.top))
            }}
            onStop={(event, data) => {
                onDragEnd(shipType)

                if (dragging) {
                    setDragging(false)
                } else {
                    onClick?.()
                }
            }}
            onDrag={(event, data) => {
                const offset = new Offset(data.deltaX, data.deltaY)
                setDragging(true)

                onDrag(offset)
            }}
        >
            <div style={{opacity: opacity}}>
                <ShipView
                    shipType={shipType}
                    orientation={orientation}
                    key={shipType.shipName}
                />
            </div>
        </DraggableCore>
    )
}

export default DraggableShipView
