import * as React from "react"
import {useState} from "react"
import BoardView from "../../Shared/Board/BoardView"
import {ConfigurableBoard} from "../../../../Domain/games/board/ConfigurableBoard"
import Grid from "@mui/material/Grid"
import Box from "@mui/material/Box"
import ShipView from "../../Shared/Ship/ShipView"
import {ShipType} from "../../../../Domain/games/ship/ShipType"
import {Orientation} from "../../../../Domain/games/ship/Orientation"
import {Board, generateEmptyMatrix, generateRandomMatrix} from "../../../../Domain/games/board/Board"
import Container from "@mui/material/Container"
import Typography from "@mui/material/Typography"
import {isValidShipCoordinate, Ship} from "../../../../Domain/games/ship/Ship"
import ErrorAlert from "../../../Shared/ErrorAlert"
import useConfigurableBoard from "./useConfigurableBoard"
import {tileSize} from "../../Shared/Board/Tile"
import {CountdownTimer} from "../../Shared/CountdownTimer/CountdownTimer"
import LeaveGameAlert from "../../Shared/LeaveGame/LeaveGameAlert"
import {Coordinate} from "../../../../Domain/games/Coordinate"
import {Offset} from "./utils/Offset"
import ShipPlacingMenuView from "./components/ShipPlacingMenuView"
import {getPosition} from "./utils/getPosition"
import PlacedDraggableShipView from "./components/PlacedDraggableShipView"
import LeaveGameButton from "../../Shared/LeaveGame/LeaveGameButton"


/**
 * Drag state.
 *
 * @property ship Ship being dragged.
 * @property isPlaced Whether the ship is placed or not.
 * @property dragOffset Offset of the drag.
 */
class DragState {
    readonly ship: Ship | null
    readonly isPlaced: boolean
    readonly dragOffset: Offset

    constructor(ship: Ship | null = null, isPlaced: boolean = false, dragOffset: Offset = Offset.ZERO) {
        this.ship = ship
        this.isPlaced = isPlaced
        this.dragOffset = dragOffset
    }

    reset(): DragState {
        return new DragState(null, false, Offset.ZERO)
    }
}

/**
 * Board setup props
 *
 * @property finalTime the time in seconds that the player has to finish the board setup
 * @property boardSize the size of the board
 * @property error the error message
 * @property ships the list of ships to be placed
 * @property onBoardReady the callback to be called when the board is ready
 * @property onLeaveGame the callback to be called when the player leaves the game
 * @property onTimeUp the callback to be called when the time is up
 */
interface BoardSetupProps {
    finalTime: number
    boardSize: number
    error: string | null
    ships: ReadonlyMap<ShipType, number>
    onBoardReady: (board: Board) => void
    onLeaveGame: () => void
    onTimeUp: () => void
}

/**
 * BoardSetup component.
 */
function BoardSetup({finalTime, boardSize, error, ships, onBoardReady, onLeaveGame, onTimeUp}: BoardSetupProps) {
    const {board, setBoard, placeShip, removeShip} = useConfigurableBoard(boardSize, generateEmptyMatrix(boardSize))
    const [unplacedShips, setUnplacedShips] = useState<ReadonlyMap<ShipType, number>>(ships)
    const [dragState, setDragState] = useState<DragState>(new DragState())
    const [leavingGame, setLeavingGame] = useState<boolean>(false)
    const boardRef = React.useRef<HTMLDivElement>(null)


    function handleDrag(offset: Offset) {
        const newDragStateOffset = dragState.dragOffset.add(offset)

        if (dragState.dragOffset.x !== newDragStateOffset.x ||
            dragState.dragOffset.y !== newDragStateOffset.y) {
            setDragState(
                new DragState(
                    dragState.ship,
                    dragState.isPlaced,
                    newDragStateOffset
                )
            )
        }
    }

    /**
     * Gets the ship when the drag ends.
     *
     * @param shipType the ship
     * @param orientation the orientation of the ship
     * @return the coordinate of the ship or null if the coordinate is not valid
     */
    function getShipAfterDragEnd(shipType: ShipType, orientation: Orientation): Ship | null {
        setDragState(dragState.reset())

        const boardOffset = getPosition(boardRef.current as HTMLElement)

        // Calculate coordinate based on offset and boardOffset
        const coordinateX = Math.round((dragState.dragOffset.x - boardOffset.left) / tileSize)
        const coordinateY = Math.round((dragState.dragOffset.y - boardOffset.top) / tileSize)

        if (!Coordinate.isValid(coordinateX, coordinateY, boardSize))
            return null

        const coordinate = new Coordinate(coordinateX, coordinateY)

        if (!isValidShipCoordinate(coordinate, orientation, shipType.size, board.size))
            return null

        return new Ship(shipType, coordinate, orientation)
    }

    return (<>
            {
                dragState.ship != null &&
                <div style={{
                    position: "absolute",
                    zIndex: 1000,
                    left: dragState.dragOffset.x,
                    top: dragState.dragOffset.y,
                }}
                >
                    <ShipView shipType={dragState.ship.type} orientation={dragState.ship.orientation}/>
                </div>
            }
            <Container maxWidth="lg">
                <Typography variant="h4">Board Setup</Typography>
                <Box sx={{mb: "5px"}}>
                    <CountdownTimer finalTime={finalTime} onTimeUp={() => {
                        onTimeUp()
                    }}/>
                </Box>

                <LeaveGameAlert
                    open={leavingGame}
                    onLeave={() => {
                        setLeavingGame(false)
                        onLeaveGame()
                    }}
                    onStay={() => setLeavingGame(false)}
                />

                <Grid container spacing={3}>
                    <Grid item lg={4} md={6} xs={12}>
                        <ShipPlacingMenuView
                            shipTypes={unplacedShips}
                            draggingUnplaced={(shipType) =>
                                dragState.ship?.type == shipType && dragState.ship != null && !dragState.isPlaced
                            }
                            onDragStart={(shipType, offset) => {
                                setDragState(
                                    new DragState(
                                        new Ship(shipType,
                                            new Coordinate(1, 1),
                                            Orientation.VERTICAL),
                                        false,
                                        offset))
                            }}
                            onDragEnd={(shipType) => {
                                const newShip = getShipAfterDragEnd(shipType, Orientation.VERTICAL)

                                if (newShip != null && board.canPlaceShip(newShip)) {
                                    placeShip(newShip)

                                    const newUnplacedShips = new Map(unplacedShips)
                                    newUnplacedShips.set(shipType, unplacedShips.get(shipType)! - 1)

                                    setUnplacedShips(newUnplacedShips)
                                }
                            }}
                            onDrag={handleDrag}
                            onRandomBoardButtonPressed={() => {
                                setBoard(new ConfigurableBoard(boardSize, generateRandomMatrix(boardSize, ships)))
                                const newUnplacedShips = new Map<ShipType, number>()
                                ships.forEach((count, ship) => newUnplacedShips.set(ship, 0))
                                setUnplacedShips(newUnplacedShips)
                            }}
                            onConfirmBoardButtonPressed={() => {
                                if (Array.from(unplacedShips.values()).filter(count => count > 0).length == 0)
                                    onBoardReady(board)
                                else
                                    alert("You must place all the ships!")
                            }}
                        />

                        <ErrorAlert error={error}/>
                    </Grid>
                    <Grid item lg={8} md={6} xs={12}>
                        <BoardView ref={boardRef} board={board} enabled={true}>
                            {
                                board.fleet.map((placedShip, index) => {
                                    return <PlacedDraggableShipView
                                        ship={placedShip}
                                        hide={dragState.ship == placedShip}
                                        onDragStart={(ship, offset) => {
                                            setDragState(
                                                new DragState(
                                                    ship,
                                                    true,
                                                    offset)
                                            )
                                        }}
                                        onDragEnd={() => {
                                            const newShip = getShipAfterDragEnd(placedShip.type, placedShip.orientation)

                                            if (newShip != null && board
                                                .removeShip(placedShip)
                                                .canPlaceShip(newShip)) {

                                                setBoard(board.removeShip(placedShip)
                                                    .placeShip(newShip))
                                            }
                                        }}

                                        onDrag={handleDrag}

                                        onClick={() => {
                                            if (!isValidShipCoordinate(
                                                placedShip.coordinate, Orientation.opposite(placedShip.orientation),
                                                placedShip.type.size, board.size)
                                            )
                                                return

                                            // Change orientation of this ship
                                            const newShip = new Ship(placedShip.type,
                                                placedShip.coordinate,
                                                Orientation.opposite(placedShip.orientation)
                                            )

                                            if (board.removeShip(placedShip).canPlaceShip(newShip))
                                                setBoard(board.removeShip(placedShip).placeShip(newShip))
                                        }
                                        }
                                    />
                                })
                            }
                        </BoardView>
                    </Grid>
                </Grid>
                <LeaveGameButton onClick={() => setLeavingGame(true)}/>
            </Container>
        </>
    )
}

export default BoardSetup
