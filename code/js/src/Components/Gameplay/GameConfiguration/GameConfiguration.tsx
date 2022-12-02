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
import * as gamesService from '../../../Services/games/GamesService';
import to from "../../../Utils/await-to";
import {useSession} from "../../../Utils/Session";
import {handleError} from "../../../Services/utils/fetchSiren";
import {useNavigate} from "react-router-dom";
import {EmbeddedLink} from "../../../Services/utils/siren/SubEntity";
import PageContent from "../../Utils/PageContent";


/**
 * GameConfiguration component.
 */
function GameConfiguration() {

    const session = useSession();
    const navigate = useNavigate();

    const [gameName, setGameName] = React.useState("Game");
    const [gridSize, setGridSize] = React.useState(defaultBoardSize);
    const [shotsPerRound, setShotsPerRound] = React.useState(1);
    const [maxTimePerRound, setMaxTimePerRound] = React.useState(100);
    const [maxTimeForLayoutPhase, setMaxTimeForLayoutPhase] = React.useState(100);
    const [shipTypes, setShipTypes] = React.useState<Map<ShipType, number>>(
        new Map<ShipType, number>(defaultShipTypes.map(ship => [ship, 1]))
    );
    const [error, setError] = React.useState<string | null>(null);

    function handleCreateGame() {
        async function createGame() {
            const [err, res] = await to(
                gamesService.createGame(
                    session!.accessToken,
                    "http://localhost:8080/games",
                    {
                        name: gameName,
                        config: {
                            gridSize,
                            maxTimePerRound,
                            shotsPerRound,
                            maxTimeForLayoutPhase,
                            shipTypes: Array.from(shipTypes.entries()).map(([shipType, count]) => ({
                                shipName: shipType.shipName,
                                size: shipType.size,
                                quantity: count,
                                points: shipType.points
                            }))
                        }
                    }
                )
            );

            if (err) {
                handleError(err, setError);
                return;
            }

            if (res?.entities === undefined)
                throw new Error("Entities are undefined");

            const game = res.entities.find(entity => entity.rel.includes("game")) as EmbeddedLink;
            if (game === undefined)
                throw new Error("Game entity not found");

            navigate(`/gameplay`, {state: {gameLink: game.href}});
        }

        createGame();
    }

    return (
        <PageContent title={"Game Configuration"} error={error}>
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
                        Grid Size {gridSize}x{gridSize}
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
                            setGridSize(value as number);
                        }}
                    />
                </Box>

                <Box>
                    <Typography id="shots-per-turn-slider" gutterBottom>
                        Shots per turn {shotsPerRound}
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
                            setShotsPerRound(value as number);
                        }}
                    />
                </Box>

                <Box>
                    <Typography id="time-per-turn-slider" gutterBottom>
                        Time per turn {maxTimePerRound} seconds
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
                            setMaxTimePerRound(value as number);
                        }}
                    />
                </Box>

                <Box>
                    <Typography id="time-for-board-configuration-slider" gutterBottom>
                        Time for board configuration
                    </Typography>
                    <Typography id="time-for-board-configuration-slider" gutterBottom>
                        {maxTimeForLayoutPhase} seconds
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
                            setMaxTimeForLayoutPhase(value as number);
                        }}
                    />
                </Box>

                <Box>
                    <Typography id="ships-selector" gutterBottom>
                        Ships
                    </Typography>
                    <Grid container spacing={2} justifyContent={"center"}>
                        {
                            Array.from(shipTypes.entries())
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
                                                        setShipTypes(new Map(shipTypes.set(ship, quantity + 1)));
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
                                                        setShipTypes(new Map(shipTypes.set(ship, quantity - 1)));
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
                            handleCreateGame();
                        }}
                    >
                        Create Game
                    </Button>
                </Box>
            </Box>
        </PageContent>
    );
}

export default GameConfiguration;
