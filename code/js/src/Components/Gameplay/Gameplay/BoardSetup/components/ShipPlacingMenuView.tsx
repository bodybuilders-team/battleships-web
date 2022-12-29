import {Card, CardActions, CardContent, Divider} from "@mui/material"
import Button from "@mui/material/Button"
import {CheckRounded, CycloneRounded} from "@mui/icons-material"
import {ShipType} from "../../../../../Domain/games/ship/ShipType"
import * as React from "react"
import {Offset} from "../utils/Offset"
import ShipSlotsView from "./ShipSlotsView"

/**
 * Ship Placing Menu View Props
 *
 * @property shipTypes Ship Types
 * @property draggingUnplaced Dragging Unplaced Callback
 * @property onDragStart Drag Start Callback
 * @property onDragEnd Drag End Callback
 * @property onDrag Drag Callback
 * @property onRandomBoardButtonPressed Random Board Button Pressed Callback
 * @property onConfirmButtonPressed Confirm Button Pressed Callback
 */
interface ShipPlacingMenuViewProps {
    shipTypes: ReadonlyMap<ShipType, number>
    draggingUnplaced: (shipType: ShipType) => boolean
    onDragStart: (shipType: ShipType, offset: Offset) => void
    onDragEnd: (shipType: ShipType) => void
    onDrag: (offset: Offset) => void
    onRandomBoardButtonPressed: () => void
    onConfirmBoardButtonPressed: () => void
}

/**
 * Ship Placing Menu View Component
 */
function ShipPlacingMenuView(
    {
        shipTypes,
        draggingUnplaced,
        onDragStart,
        onDragEnd,
        onDrag,
        onRandomBoardButtonPressed,
        onConfirmBoardButtonPressed
    }: ShipPlacingMenuViewProps) {

    return <Card>
        <CardContent>
            <ShipSlotsView
                shipTypes={shipTypes}
                draggingUnplaced={draggingUnplaced}
                onDragStart={onDragStart}
                onDragEnd={onDragEnd}
                onDrag={onDrag}
            />
        </CardContent>
        <Divider/>
        <CardActions sx={{
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center',
            alignItems: 'center',
        }}>
            <Button
                fullWidth
                size="large"
                variant="contained"
                sx={{mt: 3, mb: 2}}
                startIcon={<CycloneRounded/>}
                color="primary"
                onClick={onRandomBoardButtonPressed}
            >
                Random Board
            </Button>
            <Button
                fullWidth
                size="large"
                variant="contained"
                sx={{mt: 3, mb: 2}}
                startIcon={<CheckRounded/>}
                color="primary"
                onClick={onConfirmBoardButtonPressed}
            >
                Confirm Board
            </Button>
        </CardActions>
    </Card>
}

export default ShipPlacingMenuView