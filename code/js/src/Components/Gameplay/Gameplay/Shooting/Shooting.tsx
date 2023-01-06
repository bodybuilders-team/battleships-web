import {handleError} from "../../../../Services/utils/fetchSiren"
import * as React from "react"
import {useEffect, useState} from "react"
import Box from "@mui/material/Box"
import BoardView from "../../Shared/Board/BoardView"
import Button from "@mui/material/Button"
import {tileSize} from "../../Shared/Board/Tile"
import ShipView from "../../Shared/Ship/ShipView"
import {Coordinate} from "../../../../Domain/games/Coordinate"
import {ShipCell, UnknownShipCell} from "../../../../Domain/games/Cell"
import {CountdownTimer} from "../../Shared/CountdownTimer/CountdownTimer"
import Typography from "@mui/material/Typography"
import {Game} from "../../../../Domain/games/game/Game"
import TileHitView from "../../Shared/Board/TileHitView"
import useShooting from "./useShooting"
import LeaveGameAlert from "../../Shared/LeaveGame/LeaveGameAlert"
import LeaveGameButton from "../../Shared/LeaveGame/LeaveGameButton"
import Container from "@mui/material/Container"
import {CancelRounded, RefreshRounded} from "@mui/icons-material"
import RoundView from "./RoundView"
import {useBattleshipsService} from "../../../../Services/NavigationBattleshipsService"
import {useNavigate} from "react-router-dom"
import {Uris} from "../../../../Utils/navigation/Uris"
import ErrorAlert from "../../../Shared/ErrorAlert"
import {abortableTo} from "../../../../Utils/componentManagement/abortableUtils"
import {useMountedSignal} from "../../../../Utils/componentManagement/useMounted";
import {MyBoard} from "../../../../Domain/games/board/MyBoard";
import {OpponentBoard} from "../../../../Domain/games/board/OpponentBoard";
import GAMEPLAY_MENU = Uris.GAMEPLAY_MENU;

/**
 * Properties for the Shooting component.
 *
 * @property game the game to play
 * @property initialMyBoard the initial board of the player
 * @property initialOpponentBoard the initial board of the opponent
 * @property onFinished the callback to call when the game is finished
 * @property onTimeUp the callback to call when the time is up
 */
interface ShootingProps {
    game: Game
    initialMyBoard: MyBoard,
    initialOpponentBoard: OpponentBoard,
    onFinished: () => void
    onTimeUp: () => void
}

/**
 * Shooting component.
 */
export default function Shooting({game, initialMyBoard, initialOpponentBoard, onFinished, onTimeUp}: ShootingProps) {
    const [error, setError] = useState<string | null>(null)

    const [leavingGame, setLeavingGame] = useState<boolean>(false)
    const battleshipsService = useBattleshipsService()
    const navigate = useNavigate()
    const {shootingState, fire} = useShooting(game, initialMyBoard, initialOpponentBoard, (err) => {
        handleError(err, setError, navigate)
    })
    const mountedSignal = useMountedSignal()

    const [selectedCells, setSelectedCells] = useState<Coordinate[]>([])
    const [canFireShots, setCanFireShots] = useState<boolean>(shootingState.myTurn)

    useEffect(disableFireShotsWhenNotMyTurn, [shootingState.myTurn])
    useEffect(callOnFinishIfGameIsFinished, [shootingState.finished])

    /**
     * Disables the fire shots button when it's not my turn.
     */
    function disableFireShotsWhenNotMyTurn() {
        if (shootingState.myTurn)
            setCanFireShots(true)
    }

    /**
     * Calls the onFinished callback if the game is finished.
     */
    function callOnFinishIfGameIsFinished() {
        if (shootingState.finished)
            onFinished()
    }


    /**
     * Callback to call when the player wants to leave the game.
     */
    async function leaveGame() {
        const [err, res] = await abortableTo(battleshipsService.gamesService.leaveGame(mountedSignal))

        if (err) {
            handleError(err, setError, navigate)
            return
        }

        navigate(GAMEPLAY_MENU)
    }

    return (
        <Container maxWidth="lg">
            <Box sx={{
                display: "flex",
                flexDirection: "column",
            }}>
                <Box sx={{
                    display: "flex",
                    flexDirection: "row",
                    mt: "5px",
                    justifyContent: "space-between",
                    alignItems: "center"
                }}>
                    <CountdownTimer
                        finalTime={shootingState.gameState.phaseEndTime}
                        onTimeUp={() => {
                            setCanFireShots(false)
                            onTimeUp()
                        }}
                    />
                    <RoundView round={shootingState.gameState.round!}/>
                </Box>

                <ErrorAlert error={error}/>

                <LeaveGameAlert
                    open={leavingGame}
                    onLeave={() => {
                        setLeavingGame(false)
                        leaveGame()
                    }}
                    onStay={() => setLeavingGame(false)}
                />

                <Box sx={{
                    display: 'flex',
                    flexDirection: 'row',
                    marginTop: '15px',
                    justifyContent: "space-around",
                    flexWrap: "wrap"
                }}>
                    <Box sx={{
                        display: 'flex',
                        alignSelf: 'flex-start',
                        flexDirection: 'column'
                    }}>
                        <Typography variant="h5" sx={{textAlign: "center", mb: "5px"}}>My Board</Typography>
                        <BoardView board={shootingState.myBoard} enabled={!shootingState.myTurn}>
                            {
                                shootingState.myBoard.fleet.map((ship, index) => {
                                    return (
                                        <Box
                                            key={ship.type.shipName + index}
                                            sx={{
                                                position: 'absolute',
                                                top: (ship.coordinate.row) * tileSize,
                                                left: (ship.coordinate.col) * tileSize,
                                            }}>
                                            <ShipView
                                                shipType={ship.type}
                                                orientation={ship.orientation}
                                            />
                                        </Box>
                                    )
                                })
                            }
                            {
                                shootingState.myBoard.grid.map((cell) => {
                                    if (cell.wasHit)
                                        return (
                                            <Box sx={{
                                                position: 'absolute',
                                                top: (cell.coordinate.row) * tileSize,
                                                left: (cell.coordinate.col) * tileSize,
                                            }}>
                                                <TileHitView hitShip={
                                                    cell instanceof ShipCell || cell instanceof UnknownShipCell
                                                }/>
                                            </Box>
                                        )
                                    else
                                        return null
                                })
                            }
                        </BoardView>

                    </Box>
                    <Box sx={{
                        display: 'flex',
                        alignSelf: 'flex-start',
                        flexDirection: 'column'
                    }}>
                        <Typography variant="h5" sx={{textAlign: "center", mb: "5px"}}>Opponent Board</Typography>
                        <BoardView
                            board={shootingState.opponentBoard}
                            enabled={shootingState.myTurn}
                            onTileClicked={
                                (coordinate) => {
                                    if (!canFireShots || shootingState.opponentBoard.getCell(coordinate).wasHit)
                                        return

                                    if (selectedCells.find(c => c.equals(coordinate)) !== undefined)
                                        setSelectedCells(selectedCells.filter(c => !c.equals(coordinate)))
                                    else if (selectedCells.length < game.config.shotsPerRound)
                                        setSelectedCells([...selectedCells, coordinate])
                                    else if (game.config.shotsPerRound == 1)
                                        setSelectedCells([coordinate])
                                }
                            }>
                            {
                                shootingState.opponentBoard.fleet.map((ship, index) => {
                                    return (
                                        <Box
                                            key={ship.type.shipName + index}
                                            sx={{
                                                position: 'absolute',
                                                top: (ship.coordinate.row) * tileSize,
                                                left: (ship.coordinate.col) * tileSize,
                                            }}>
                                            <ShipView
                                                shipType={ship.type}
                                                orientation={ship.orientation}
                                            />
                                        </Box>
                                    )
                                })
                            }
                            {
                                selectedCells.map((coordinate) => {
                                    return (
                                        <Box sx={{
                                            position: 'absolute',
                                            top: (coordinate.row) * tileSize,
                                            left: (coordinate.col) * tileSize,
                                            width: tileSize,
                                            height: tileSize,
                                            border: '4px solid green',
                                            pointerEvents: "none",
                                        }}/>
                                    )
                                })
                            }
                            {
                                shootingState.opponentBoard.grid.map((cell) => {
                                    if (cell.wasHit)
                                        return (
                                            <Box sx={{
                                                position: 'absolute',
                                                top: (cell.coordinate.row) * tileSize,
                                                left: (cell.coordinate.col) * tileSize,
                                            }}>
                                                <TileHitView hitShip={
                                                    cell instanceof ShipCell || cell instanceof UnknownShipCell
                                                }/>
                                            </Box>
                                        )
                                    else
                                        return null
                                })
                            }
                        </BoardView>
                        <Box sx={{
                            display: 'flex',
                            flexDirection: 'row',
                            justifyContent: 'space-around',
                            width: '100%',
                        }}>
                            <Button
                                size="large"
                                variant="contained"
                                sx={{mt: 3, mb: 2, width: '40%'}}
                                disabled={!canFireShots || selectedCells.length == 0}
                                startIcon={<CancelRounded/>}
                                color="primary"
                                onClick={() => {
                                    setCanFireShots(false)
                                    const shots = selectedCells
                                    setSelectedCells([])
                                    fire(shots)
                                }}
                            >
                                Shoot
                            </Button>
                            <Button
                                size="large"
                                variant="contained"
                                sx={{mt: 3, mb: 2, width: '40%'}}
                                disabled={!canFireShots || selectedCells.length == 0}
                                startIcon={<RefreshRounded/>}
                                color="primary"
                                onClick={() => {
                                    setSelectedCells([])
                                }}
                            >
                                Reset Shots
                            </Button>
                        </Box>
                    </Box>
                </Box>
            </Box>

            <LeaveGameButton onClick={() => setLeavingGame(true)}/>
        </Container>
    )
}
