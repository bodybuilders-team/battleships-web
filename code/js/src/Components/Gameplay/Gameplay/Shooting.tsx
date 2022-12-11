import {handleError} from "../../../Services/utils/fetchSiren";
import {Game, GameState} from "./Gameplay";
import {MyBoard} from "../../../Domain/games/board/MyBoard";
import {OpponentBoard} from "../../../Domain/games/board/OpponentBoard";
import * as React from "react";
import {useEffect} from "react";
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService";
import Box from "@mui/material/Box";
import BoardView from "../Shared/Board/BoardView";
import Button from "@mui/material/Button";
import {useNavigate} from "react-router-dom";
import {Ship} from "../../../Domain/games/ship/Ship";
import {tileSize} from "../Shared/Board/Tile";
import ShipView from "../Shared/Ship/ShipView";
import {useSession} from "../../../Utils/Session";
import {Coordinate} from "../../../Domain/games/Coordinate";
import to from "../../../Utils/await-to";
import {throwError} from "../../../Services/utils/errorUtils";
import {ShipType} from "../../../Domain/games/ship/ShipType";
import {Orientation} from "../../../Domain/games/ship/Orientation";
import {ShipCell, UnknownShipCell} from "../../../Domain/games/Cell";
import {useInterval, useTimeout} from "../../../Hooks/useInterval";
import {CountdownTimer} from "../../Utils/CountdownTimer";
import Typography from "@mui/material/Typography";

const TURN_SWITCH_DELAY = 1000
const POLLING_DELAY = 1000

interface ShootingProps {
    game: Game;
    myFleet: Ship[];
}


export enum ShotResult {
    HIT,
    MISS,
    SUNK
}

export namespace ShotResult {
    export function parse(shotResult: string): ShotResult {
        switch (shotResult) {
            case "HIT":
                return ShotResult.HIT
            case "MISS":
                return ShotResult.MISS
            case "SUNK":
                return ShotResult.SUNK
        }

        throw new Error("Unknown shot result")
    }
}

export class FiredShot {
    readonly coordinate: Coordinate
    readonly round: number
    readonly result: ShotResult
    readonly sunkShip: Ship | null

    constructor(firedShotModel: FiredShotModel, shipTypes: ReadonlyMap<ShipType, number>) {
        this.coordinate = Coordinate.fromCoordinateModel(firedShotModel.coordinate);
        this.round = firedShotModel.round;
        this.result = ShotResult.parse(firedShotModel.result.result);

        this.sunkShip = firedShotModel.sunkShip != null ? new Ship(
            Array.from(shipTypes.keys()).find((shipType) =>
                shipType.shipName === firedShotModel.sunkShip?.type
            ) ?? throwError("Ship type not found"),
            Coordinate.fromCoordinateModel(firedShotModel.sunkShip?.coordinate
                ?? throwError("Sunk ship coordinate is null")),
            Orientation.parse(firedShotModel.sunkShip?.orientation
                ?? throwError("Sunk ship orientation is null"))
        ) : null;
    }
}

interface ShootingState {
    gameState: GameState,
    myBoard: MyBoard,
    opponentBoard: OpponentBoard,
    winner: string | null,
    finished: boolean,
    myTurn: boolean
}


function useShooting(game: Game, myFleet: Ship[], onError: (error: Error) => void, onLinkMissing: (rel: string) => void) {
    const session = useSession()
    const battleshipsService = useBattleshipsService();

    // Combine state into one object, to do this we need to separate api calls from state updates
    // Maybe we can do this with a redux?
    const [gameState, setGameState] = React.useState<GameState>(game.state)
    const [myBoard, setMyBoard] = React.useState<MyBoard>(MyBoard.fromFleet(game.config.gridSize, myFleet))
    const [opponentBoard, setOpponentBoard] = React.useState<OpponentBoard>(new OpponentBoard(game.config.gridSize))
    const [winner, setWinner] = React.useState<string | null>(null)
    const [finished, setFinished] = React.useState<boolean>(false)
    const [myTurn, setMyTurn] = React.useState<boolean>(game.state.turn == session!.username)
    const [switchTurnWithDelay, setSwitchTurnWithDelay] = React.useState<boolean>(false)

    useInterval(async () => {
        if (myTurn)
            return true

        const [err, res] = await to(battleshipsService.gamesService.getGameState())

        if (err) {
            onError(err)
            return true
        }

        const gameState = new GameState(res?.properties!)

        if (gameState.phase === "FINISHED") {
            setFinished(true)
            return true
        }

        setGameState(gameState)

        if (gameState.turn === session!.username) {
            const opponentShots = await getOpponentShots()
            if (opponentShots == null)
                return true

            setMyBoard(MyBoard.fromFleet(myBoard.size, myBoard.fleet).shoot(opponentShots))
            setSwitchTurnWithDelay(true)
            return true
        }

        return false
    }, POLLING_DELAY, [myTurn])

    useTimeout(() => {
        if (!switchTurnWithDelay)
            return

        setSwitchTurnWithDelay(false)
        setMyTurn(!myTurn)
    }, TURN_SWITCH_DELAY, [switchTurnWithDelay])

    async function fire(shots: Coordinate[]) {
        const [err, res] = await to(battleshipsService.playersService.fireShots({
            shots: shots.map(shot => {
                return {coordinate: shot.toCoordinateModel()}
            })
        }))

        if (err) {
            onError(err)
            return
        }

        const shipTypes = game.config.shipTypes ?? throwError("Ship types are missing")

        const firedShots = res?.properties?.shots.map((shot) => {
            return new FiredShot(shot, shipTypes)
        }) ?? throwError("No shots found")

        setOpponentBoard(opponentBoard.updateWith(firedShots))
        setSwitchTurnWithDelay(true)
    }

    async function getOpponentShots() {
        const [err, res] = await to(battleshipsService.playersService.getOpponentShots())

        if (err) {
            onError(err)
            return
        }

        return res?.properties?.shots.map((shot) => {
            return Coordinate.fromCoordinateModel(shot.coordinate)
        }) ?? throwError("No shots found")
    }

    return {shootingState: {gameState, myBoard, opponentBoard, finished, myTurn}, fire}
}

export function TileHitView({hitShip}: { hitShip: boolean }) {
    const color = hitShip ? "red" : "grey"
    return <svg width={tileSize} height={tileSize}>
        <line x1={0} y1={0} x2={tileSize} y2={tileSize} stroke={color} strokeWidth={2}/>
        <line x1={0} y1={tileSize} x2={tileSize} y2={0} stroke={color} strokeWidth={2}/>
    </svg>
}

function Shooting({game, myFleet}: ShootingProps) {
    const [error, setError] = React.useState<string | null>(null);
    const navigate = useNavigate();

    const {shootingState, fire} = useShooting(game, myFleet, (err) => {
        handleError(err, setError);
    }, (rel) => {
        navigate("/")
    })

    const [selectedCells, setSelectedCells] = React.useState<Coordinate[]>([]);
    const [canFireShots, setCanFireShots] = React.useState<boolean>(shootingState.myTurn);

    useEffect(() => {
        if (shootingState.myTurn)
            setCanFireShots(true);
    }, [shootingState.myTurn])

    return (
        <Box sx={{
            display: "flex",
            flexDirection: "column",
        }}>
            <Box sx={{display:"flex", mt: "5px", justifyContent:"center"}}>
                <CountdownTimer finalTime={shootingState.gameState.phaseEndTime} onTimeUp={() => setCanFireShots(false)}/>
            </Box>

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
                    <Typography variant="h5" sx={{textAlign: "center", mb:"5px"}}>My Board</Typography>
                    <BoardView board={shootingState.myBoard} enabled={!shootingState.myTurn}>
                        {shootingState.myBoard.fleet.map((ship, index) => {
                            return <Box sx={{
                                position: 'absolute',
                                top: (ship.coordinate.row) * tileSize,
                                left: (ship.coordinate.col) * tileSize,
                            }}>
                                <ShipView
                                    type={ship.type}
                                    orientation={ship.orientation}
                                    key={ship.type.shipName + index}
                                />

                            </Box>
                        })}
                        {shootingState.myBoard.grid.map((cell) => {
                            if (cell.wasHit)
                                return <Box sx={{
                                    position: 'absolute',
                                    top: (cell.coordinate.row) * tileSize,
                                    left: (cell.coordinate.col) * tileSize,
                                }}>
                                    <TileHitView hitShip={cell instanceof ShipCell || cell instanceof UnknownShipCell}/>
                                </Box>
                            else
                                return null
                        })}
                    </BoardView>

                </Box>
                <Box sx={{
                    display: 'flex',
                    alignSelf: 'flex-start',
                    flexDirection: 'column'
                }}>
                    <Typography variant="h5" sx={{textAlign: "center", mb:"5px"}}>Opponent Board</Typography>
                    <BoardView board={shootingState.opponentBoard} enabled={shootingState.myTurn} onTileClicked={
                        (coordinate) => {
                            console.log(shootingState.opponentBoard)
                            if (!canFireShots || shootingState.opponentBoard.getCell(coordinate).wasHit)
                                return

                            if (selectedCells.find(c => c.equals(coordinate)) !== undefined) {
                                setSelectedCells(selectedCells.filter(c => !c.equals(coordinate)))
                            } else if (selectedCells.length < game.config.shotsPerRound) {
                                setSelectedCells([...selectedCells, coordinate])
                            } else if (game.config.shotsPerRound == 1) {
                                setSelectedCells([coordinate])
                            }
                        }
                    }>

                        {shootingState.opponentBoard.fleet.map((ship, index) => {
                            return <Box sx={{
                                position: 'absolute',
                                top: (ship.coordinate.row) * tileSize,
                                left: (ship.coordinate.col) * tileSize,
                            }}>
                                <ShipView
                                    type={ship.type}
                                    orientation={ship.orientation}
                                    key={ship.type.shipName + index}
                                />

                            </Box>
                        })}
                        {selectedCells.map((coordinate) => {
                            return <Box sx={{
                                position: 'absolute',
                                top: (coordinate.row) * tileSize,
                                left: (coordinate.col) * tileSize,
                                width: tileSize,
                                height: tileSize,
                                border: '4px solid green',
                                pointerEvents: "none",
                            }}>
                            </Box>
                        })
                        }
                        {shootingState.opponentBoard.grid.map((cell) => {
                            if (cell.wasHit)
                                return <Box sx={{
                                    position: 'absolute',
                                    top: (cell.coordinate.row) * tileSize,
                                    left: (cell.coordinate.col) * tileSize,
                                }}>
                                    <TileHitView hitShip={cell instanceof ShipCell || cell instanceof UnknownShipCell}/>
                                </Box>
                            else
                                return null
                        })}
                    </BoardView>
                    <Box sx={
                        {
                            display: 'flex',
                            flexDirection: 'row',
                            justifyContent: 'center'
                        }
                    }>
                        <Button color="primary" disabled={!canFireShots} onClick={() => {
                            setCanFireShots(false);
                            const shots = selectedCells
                            setSelectedCells([])
                            fire(shots)
                        }}>
                            Shoot
                        </Button>
                        <Button color="primary" disabled={!canFireShots} onClick={() => {
                            setSelectedCells([])
                        }}>
                            Reset Shots
                        </Button>
                    </Box>
                </Box>
            </Box>
        </Box>
    )
}

export default Shooting;