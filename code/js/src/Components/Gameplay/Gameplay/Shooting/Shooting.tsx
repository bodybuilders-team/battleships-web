import {handleError} from "../../../../Services/utils/fetchSiren";
import * as React from "react";
import {useEffect, useState} from "react";
import Box from "@mui/material/Box";
import BoardView from "../../Shared/Board/BoardView";
import Button from "@mui/material/Button";
import {Ship} from "../../../../Domain/games/ship/Ship";
import {tileSize} from "../../Shared/Board/Tile";
import ShipView from "../../Shared/Ship/ShipView";
import {Coordinate} from "../../../../Domain/games/Coordinate";
import {ShipCell, UnknownShipCell} from "../../../../Domain/games/Cell";
import {CountdownTimer} from "../../../Shared/CountdownTimer/CountdownTimer";
import Typography from "@mui/material/Typography";
import {Game} from "../../../../Domain/games/game/Game";
import TileHitView from "../../Shared/Board/TileHitView";
import useShooting from "./useShooting";
import LeaveGameAlert from "../../Shared/LeaveGameAlert";
import LeaveGameButton from "../../Shared/LeaveGameButton";
import Container from "@mui/material/Container";

/**
 * Properties for the Shooting component.
 *
 * @property game the game to play
 * @property myFleet the fleet of the player
 */
interface ShootingProps {
    game: Game;
    myFleet: Ship[];
}

/**
 * Shooting component.
 */
export default function Shooting({game, myFleet}: ShootingProps) {
    const [error, setError] = useState<string | null>(null);

    const [leavingGame, setLeavingGame] = useState<boolean>(false);

    const {shootingState, fire} = useShooting(game, myFleet, (err) => {
        handleError(err, setError);
    });

    const [selectedCells, setSelectedCells] = useState<Coordinate[]>([]);
    const [canFireShots, setCanFireShots] = useState<boolean>(shootingState.myTurn);

    useEffect(() => {
        if (shootingState.myTurn)
            setCanFireShots(true);
    }, [shootingState.myTurn]);

    return (

        <Container maxWidth="lg">
            <Box sx={{
                display: "flex",
                flexDirection: "column",
            }}>
                <Box sx={{display: "flex", mt: "5px", justifyContent: "center"}}>
                    <CountdownTimer
                        finalTime={shootingState.gameState.phaseEndTime}
                        onTimeUp={() => setCanFireShots(false)}
                    />
                </Box>

                <LeaveGameAlert
                    open={leavingGame}
                    onLeave={() => {
                        setLeavingGame(false);
                        // TODO: leave game
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
                                        <TileHitView
                                            hitShip={cell instanceof ShipCell || cell instanceof UnknownShipCell}/>
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
                        <Typography variant="h5" sx={{textAlign: "center", mb: "5px"}}>Opponent Board</Typography>
                        <BoardView board={shootingState.opponentBoard} enabled={shootingState.myTurn} onTileClicked={
                            (coordinate) => {
                                if (!canFireShots || shootingState.opponentBoard.getCell(coordinate).wasHit)
                                    return;

                                if (selectedCells.find(c => c.equals(coordinate)) !== undefined)
                                    setSelectedCells(selectedCells.filter(c => !c.equals(coordinate)));
                                else if (selectedCells.length < game.config.shotsPerRound)
                                    setSelectedCells([...selectedCells, coordinate]);
                                else if (game.config.shotsPerRound == 1)
                                    setSelectedCells([coordinate]);
                            }
                        }>
                            {
                                shootingState.opponentBoard.fleet.map((ship, index) => {
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
                                })
                            }
                            {
                                selectedCells.map((coordinate) => {
                                    return <Box sx={{
                                        position: 'absolute',
                                        top: (coordinate.row) * tileSize,
                                        left: (coordinate.col) * tileSize,
                                        width: tileSize,
                                        height: tileSize,
                                        border: '4px solid green',
                                        pointerEvents: "none",
                                    }}/>;
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
                                                <TileHitView
                                                    hitShip={cell instanceof ShipCell || cell instanceof UnknownShipCell}/>
                                            </Box>
                                        );
                                    else
                                        return null;
                                })
                            }
                        </BoardView>
                        <Box sx={{
                            display: 'flex',
                            flexDirection: 'row',
                            justifyContent: 'center'
                        }}>
                            <Button color="primary" disabled={!canFireShots} onClick={() => {
                                setCanFireShots(false);
                                const shots = selectedCells;
                                setSelectedCells([]);
                                fire(shots);
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

            <LeaveGameButton onClick={() => setLeavingGame(true)}/>
        </Container>
    )
}
