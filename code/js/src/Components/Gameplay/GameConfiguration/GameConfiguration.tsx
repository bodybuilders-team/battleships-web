import * as React from "react";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import {defaultShipTypes, ShipType} from "../../../Domain/games/ship/ShipType";
import TextField from "@mui/material/TextField";
import {Add, Remove} from "@mui/icons-material";
import {Slider} from "@mui/material";
import IconButton from "@mui/material/IconButton";
import Grid from "@mui/material/Grid";
import ShipView from "../Shared/Ship/ShipView";
import {Orientation} from "../../../Domain/games/ship/Orientation";
import {defaultBoardSize, maxBoardSize, minBoardSize} from "../../../Domain/games/board/Board";


/**
 * GameConfiguration component.
 */
function GameConfiguration() {

    const [gameName, setGameName] = React.useState("");
    const [boardSize, setBoardSize] = React.useState(defaultBoardSize);
    const [shotsPerTurn, setShotsPerTurn] = React.useState(1);
    const [timePerTurn, setTimePerTurn] = React.useState(60);
    const [timeForBoardConfiguration, setTimeForBoardConfiguration] = React.useState(60);
    const [ships, setShips] = React.useState<Map<ShipType, number>>(
        new Map<ShipType, number>(defaultShipTypes.map(ship => [ship, 1]))
    );

    return (
        <Box
            sx={{
                marginTop: 8,
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
            }}
        >
            <Typography sx={{mb: 3}} variant="h4">Game Configuration</Typography>
            <Box sx={{
                mt: 1,
                alignItems: 'center',
                width: 400,
            }}>
                <TextField
                    margin="normal"
                    fullWidth
                    label="Game Name"
                    name="gameName"
                    onChange={(event) => {
                        setGameName(event.target.value);
                    }}
                />

                <Box>
                    <Typography id="board-size-slider" gutterBottom>
                        Grid Size {boardSize}x{boardSize}
                    </Typography>
                    <Slider
                        defaultValue={defaultBoardSize}
                        aria-labelledby="board-size-slider"
                        valueLabelDisplay="auto"
                        step={1}
                        marks
                        min={minBoardSize}
                        max={maxBoardSize}
                        onChange={(event, value) => {
                            setBoardSize(value as number);
                        }}
                    />
                </Box>

                <Box>
                    <Typography id="shots-per-turn-slider" gutterBottom>
                        Shots per turn {shotsPerTurn}
                    </Typography>
                    <Slider
                        defaultValue={1}
                        aria-labelledby="shots-per-turn-slider"
                        valueLabelDisplay="auto"
                        step={1}
                        marks
                        min={1}
                        max={5}
                        onChange={(event, value) => {
                            setShotsPerTurn(value as number);
                        }}
                    />
                </Box>

                <Box>
                    <Typography id="time-per-turn-slider" gutterBottom>
                        Time per turn {timePerTurn} seconds
                    </Typography>
                    <Slider
                        defaultValue={60}
                        aria-labelledby="time-per-turn-slider"
                        valueLabelDisplay="auto"
                        step={10}
                        marks
                        min={30}
                        max={120}
                        onChange={(event, value) => {
                            setTimePerTurn(value as number);
                        }}
                    />
                </Box>

                <Box>
                    <Typography id="time-for-board-configuration-slider" gutterBottom>
                        Time for board configuration
                    </Typography>
                    <Typography id="time-for-board-configuration-slider" gutterBottom>
                        {timeForBoardConfiguration} seconds
                    </Typography>
                    <Slider
                        defaultValue={60}
                        aria-labelledby="time-for-board-configuration-slider"
                        valueLabelDisplay="auto"
                        step={10}
                        marks
                        min={30}
                        max={120}
                        onChange={(event, value) => {
                            setTimeForBoardConfiguration(value as number);
                        }}
                    />
                </Box>

                <Box>
                    <Typography id="ships-selector" gutterBottom>
                        Ships
                    </Typography>
                    <Grid container spacing={2} justifyContent={"center"}>
                        {
                            Array.from(ships.entries())
                                .map(([ship, quantity]) => {
                                    return (
                                        <Grid item key={ship.shipName}>
                                            <Box sx={{height: 200}}>
                                                <ShipView type={ship} orientation={Orientation.VERTICAL}/>
                                            </Box>
                                            <IconButton
                                                aria-label="add"
                                                color="primary"
                                                onClick={() => {
                                                    if (quantity < 5)
                                                        setShips(new Map(ships.set(ship, quantity + 1)));
                                                }}
                                            >
                                                <Add/>
                                            </IconButton>
                                            <Typography id="ship-quantity-selector" gutterBottom>{quantity}</Typography>
                                            <IconButton
                                                aria-label="remove"
                                                color="primary"
                                                onClick={() => {
                                                    if (quantity > 0)
                                                        setShips(new Map(ships.set(ship, quantity - 1)));
                                                }}
                                            >
                                                <Remove/>
                                            </IconButton>
                                        </Grid>
                                    );
                                })
                        }
                    </Grid>

                    <Button
                        fullWidth
                        size="large"
                        variant="contained"
                        sx={{mt: 3, mb: 2}}
                        startIcon={<Add/>}
                        color="primary"
                        onClick={() => {
                        }}
                    >
                        Create Game
                    </Button>
                </Box>
            </Box>
        </Box>
    );
}

export default GameConfiguration;
