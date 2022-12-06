import * as React from "react";
import BoardView from "../Shared/Board/BoardView";
import {ConfigurableBoard} from "../../../Domain/games/board/ConfigurableBoard";
import Grid from "@mui/material/Grid";
import {Card, CardActions, CardContent, Divider} from "@mui/material";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import ShipView from "../Shared/Ship/ShipView";
import {ShipType} from "../../../Domain/games/ship/ShipType";
import {Orientation} from "../../../Domain/games/ship/Orientation";
import {Board, generateEmptyMatrix, generateRandomMatrix} from "../../../Domain/games/board/Board";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import {isValidShipCoordinate, Ship} from "../../../Domain/games/ship/Ship";
import {Coordinate} from "../../../Domain/games/Coordinate";
import {ErrorAlert} from "../../Utils/ErrorAlert";
import {useConfigurableBoard} from "../../../Hooks/useBoard";
import {tileSize} from "../Shared/Board/Tile";

/**
 * BoardSetup component.
 *
 * @param boardSize the size of the board
 * @param ships the list of ships to be placed
 * @param onBoardReady the callback to be called when the board is ready
 */
function BoardSetup({
                        boardSize,
                        ships,
                        onBoardReady
                    }: { boardSize: number, ships: Map<ShipType, number>, onBoardReady: (board: Board) => void },) {
    const {board, setBoard, placeShip, removeShip} = useConfigurableBoard(boardSize, generateEmptyMatrix(boardSize))
    const [unplacedShips, setUnplacedShips] = React.useState<ReadonlyMap<ShipType, number>>(ships);
    const [selectedShipType, setSelectedShipType] = React.useState<ShipType | null>(null);
    const [error, setError] = React.useState<string | null>(null);

    return (
        <Box component="main">
            <Container maxWidth="lg">
                <Typography sx={{mb: 3}} variant="h4">Board Setup</Typography>
                <Grid container spacing={3}>
                    <Grid item lg={4} md={6} xs={12}>
                        <Card>
                            <CardContent>
                                <Box
                                    sx={{
                                        display: 'flex',
                                        flexDirection: 'row',
                                        justifyContent: 'space-between'
                                    }}
                                >
                                    {
                                        Array.from(unplacedShips.entries()).map(([ship, count]) =>
                                            count > 0 ?
                                                <Box
                                                    sx={{
                                                        display: 'flex',
                                                        flexDirection: 'column',
                                                        justifyContent: 'space-between',
                                                    }}>
                                                    <Box sx={{
                                                        display: 'flex',
                                                        marginTop: '10px',
                                                        border: selectedShipType === ship ? '2px solid green' : 'none'
                                                    }}>
                                                        <ShipView
                                                            type={ship}
                                                            orientation={Orientation.VERTICAL}
                                                            key={ship.shipName}
                                                            onClick={() => {
                                                                setSelectedShipType(ship);
                                                            }}
                                                        />
                                                    </Box>
                                                    <Box sx={{
                                                        marginTop: '10px',
                                                    }}>
                                                        {count}
                                                    </Box>
                                                </Box> : <></>
                                        )
                                    }
                                </Box>
                            </CardContent>
                            <Divider/>
                            <CardActions>
                                <Button color="primary" fullWidth onClick={() => {
                                    setBoard(new ConfigurableBoard(boardSize, generateRandomMatrix(boardSize, ships)));
                                    setUnplacedShips(new Map());
                                }}>
                                    Random Board
                                </Button>
                                <Button color="primary" fullWidth onClick={() => {
                                    if (unplacedShips.size === 0) {
                                        onBoardReady(board);
                                    } else {
                                        alert("You must place all the ships!");
                                    }
                                }}>
                                    Confirm Board
                                </Button>
                            </CardActions>
                        </Card>
                        <ErrorAlert error={error}/>
                    </Grid>
                    <Grid item lg={8} md={6} xs={12}>

                        <BoardView board={board} onTileClicked={
                            (row: number, col: number) => {
                                if (selectedShipType == null)
                                    return

                                if (!isValidShipCoordinate(new Coordinate(row, col),
                                    Orientation.VERTICAL, selectedShipType.size, board.size))
                                    return

                                const ship = new Ship(selectedShipType, new Coordinate(row, col), Orientation.VERTICAL);
                                if (board.canPlaceShip(ship)) {
                                    placeShip(ship);

                                    const newUnplacedShips = new Map(unplacedShips);
                                    newUnplacedShips.set(selectedShipType, unplacedShips.get(selectedShipType)! - 1)

                                    setUnplacedShips(newUnplacedShips);
                                    setSelectedShipType(null);
                                    setError(null);
                                }
                            }
                        }>
                            {board.fleet.map((ship, index) => {
                                return <Box sx={{
                                    position: 'absolute',
                                    top: (ship.coordinate.row) * tileSize,
                                    left: (ship.coordinate.col) * tileSize,
                                    zIndex: 1
                                }}>
                                    <ShipView
                                        type={ship.type}
                                        orientation={ship.orientation}
                                        key={ship.type.shipName + index}
                                        onClick={() => {
                                            if (!isValidShipCoordinate(ship.coordinate, Orientation.opposite(ship.orientation), ship.type.size, board.size))
                                                return

                                            //Change orientation of this ship
                                            const newShip = new Ship(ship.type,
                                                ship.coordinate,
                                                Orientation.opposite(ship.orientation));

                                            if (board
                                                .removeShip(ship)
                                                .canPlaceShip(newShip)) {
                                                setBoard(board.removeShip(ship).placeShip(newShip));
                                            }
                                        }
                                        }
                                    />
                                </Box>
                            })}
                        </BoardView>

                    </Grid>
                </Grid>
            </Container>
        </Box>
    );
}

export default BoardSetup;
