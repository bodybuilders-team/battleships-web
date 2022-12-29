import {ShipType} from "../../../../../Domain/games/ship/ShipType"
import {Offset} from "../utils/Offset"
import {DraggableCore} from "react-draggable"
import {getPosition} from "../utils/getPosition"
import ShipView from "../../../Shared/Ship/ShipView"
import {Orientation} from "../../../../../Domain/games/ship/Orientation"
import * as React from "react"

/**
 * Draggabçe Ship View Props
 *
 * @property shipType Ship Type
 * @property orientation Ship Orientation
 * @property onDragStart Drag Start Callback
 * @property onDragEnd Drag End Callback
 * @property onDrag Drag Callback
 * @property onClick Click Callback
 * @property opacity Opacity of the ship
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
function DraggableShipView({
                               shipType,
                               orientation,
                               onDragStart,
                               onDragEnd,
                               onDrag,
                               onClick,
                               opacity
                           }: DraggableShipViewProps) {
    const [dragging, setDragging] = React.useState(false)

    return <DraggableCore
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
}

export default DraggableShipView