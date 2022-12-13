import * as React from "react";
import {useState} from "react";
import BoardView from "../../Shared/Board/BoardView";
import {ConfigurableBoard} from "../../../../Domain/games/board/ConfigurableBoard";
import Grid from "@mui/material/Grid";
import {Card, CardActions, CardContent, Divider} from "@mui/material";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import ShipView from "../../Shared/Ship/ShipView";
import {ShipType} from "../../../../Domain/games/ship/ShipType";
import {Orientation} from "../../../../Domain/games/ship/Orientation";
import {Board, generateEmptyMatrix, generateRandomMatrix} from "../../../../Domain/games/board/Board";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import {isValidShipCoordinate, Ship} from "../../../../Domain/games/ship/Ship";
import ErrorAlert from "../../../Shared/ErrorAlert";
import useConfigurableBoard from "./useConfigurableBoard";
import {tileSize} from "../../Shared/Board/Tile";
import {CountdownTimer} from "../../Shared/CountdownTimer/CountdownTimer";
import LeaveGameAlert from "../../Shared/LeaveGame/LeaveGameAlert";
import LeaveGameButton from "../../Shared/LeaveGame/LeaveGameButton";
import {CheckRounded, CycloneRounded} from "@mui/icons-material";

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
    finalTime: number;
    boardSize: number;
    error: string | null;
    ships: ReadonlyMap<ShipType, number>;
    onBoardReady: (board: Board) => void;
    onLeaveGame: () => void;
    onTimeUp: () => void;
}

/**
 * BoardSetup component.
 */
function BoardSetup({finalTime, boardSize, error, ships, onBoardReady, onLeaveGame, onTimeUp}: BoardSetupProps) {
    const {board, setBoard, placeShip, removeShip} = useConfigurableBoard(boardSize, generateEmptyMatrix(boardSize));
    const [unplacedShips, setUnplacedShips] = useState<ReadonlyMap<ShipType, number>>(ships);
    const [selectedShipType, setSelectedShipType] = useState<ShipType | null>(null);
    const [leavingGame, setLeavingGame] = useState<boolean>(false);

    return (
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
                    setLeavingGame(false);
                    onLeaveGame();
                }}
                onStay={() => setLeavingGame(false)}
            />

            <Grid container spacing={3}>
                <Grid item lg={4} md={6} xs={12}>
                    <Card>
                        <CardContent>
                            <Box sx={{
                                display: 'flex',
                                flexDirection: 'row',
                                justifyContent: 'space-between'
                            }}>
                                {
                                    Array.from(unplacedShips.entries()).map(([ship, count]) =>
                                        <Box key={ship.shipName} sx={{
                                            display: 'flex',
                                            flexDirection: 'column',
                                            justifyContent: 'space-between',
                                        }}>
                                            <Box sx={{
                                                display: 'flex',
                                                marginTop: '10px',
                                                border: selectedShipType === ship ? '2px solid red' : 'none',
                                                opacity: count === 0 ? 0.5 : 1
                                            }}>
                                                <ShipView
                                                    type={ship}
                                                    orientation={Orientation.VERTICAL}
                                                    key={ship.shipName}
                                                    onClick={() => {
                                                        if (count > 0)
                                                            setSelectedShipType(ship);
                                                    }}
                                                />
                                            </Box>
                                            <Box sx={{marginTop: '10px'}}>{count}</Box>
                                        </Box>
                                    )
                                }
                            </Box>
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
                                onClick={() => {
                                    setBoard(new ConfigurableBoard(boardSize, generateRandomMatrix(boardSize, ships)));
                                    const newUnplacedShips = new Map<ShipType, number>();
                                    ships.forEach((count, ship) => newUnplacedShips.set(ship, 0));
                                    setUnplacedShips(newUnplacedShips);
                                }}
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
                                onClick={() => {
                                    if (Array.from(unplacedShips.values()).filter(count => count > 0).length == 0)
                                        onBoardReady(board);
                                    else
                                        alert("You must place all the ships!");
                                }}
                            >
                                Confirm Board
                            </Button>
                        </CardActions>
                    </Card>
                    <ErrorAlert error={error}/>
                </Grid>
                <Grid item lg={8} md={6} xs={12}>
                    <BoardView board={board} enabled={true} onTileClicked={
                        (coordinate) => {
                            if (selectedShipType == null || unplacedShips.get(selectedShipType) == 0)
                                return;

                            if (!isValidShipCoordinate(coordinate,
                                Orientation.VERTICAL, selectedShipType.size, board.size)
                            )
                                return;

                            const ship = new Ship(selectedShipType, coordinate, Orientation.VERTICAL);
                            if (board.canPlaceShip(ship)) {
                                placeShip(ship);

                                const newUnplacedShips = new Map(unplacedShips);
                                newUnplacedShips.set(selectedShipType, unplacedShips.get(selectedShipType)! - 1);

                                setUnplacedShips(newUnplacedShips);
                                setSelectedShipType(null);
                            }
                        }
                    }>
                        {
                            board.fleet.map((ship, index) => {
                                return <Box key={ship.type.shipName} sx={{
                                    position: 'absolute',
                                    top: (ship.coordinate.row) * tileSize,
                                    left: (ship.coordinate.col) * tileSize,
                                }}>
                                    <ShipView
                                        type={ship.type}
                                        orientation={ship.orientation}
                                        key={ship.type.shipName + index}
                                        onClick={() => {
                                            if (!isValidShipCoordinate(
                                                ship.coordinate, Orientation.opposite(ship.orientation),
                                                ship.type.size, board.size)
                                            )
                                                return;

                                            // Change orientation of this ship
                                            const newShip = new Ship(ship.type,
                                                ship.coordinate,
                                                Orientation.opposite(ship.orientation)
                                            );

                                            if (board.removeShip(ship).canPlaceShip(newShip))
                                                setBoard(board.removeShip(ship).placeShip(newShip));
                                        }}
                                    />
                                </Box>
                            })
                        }
                    </BoardView>
                </Grid>
            </Grid>
            <LeaveGameButton onClick={() => setLeavingGame(true)}/>
        </Container>
    );
}

export default BoardSetup;