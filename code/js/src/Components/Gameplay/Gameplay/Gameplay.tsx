import * as React from "react";
import {useEffect} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import to from "../../../Utils/await-to";
import {useSession} from "../../../Utils/Session";
import {handleError} from "../../../Services/utils/fetchSiren";
import BoardSetup from "../BoardSetup/BoardSetup";
import {GetGameOutputModel} from "../../../Services/games/models/games/getGame/GetGameOutput";
import LoadingSpinner from "../../Utils/LoadingSpinner";
import PageContent from "../../Utils/PageContent";
import BoardView from "../Shared/Board/BoardView";
import {Board} from "../../../Domain/games/board/Board";
import {Card, CardActions, CardContent, Divider} from "@mui/material";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService";
import {Rels} from "../../../Services/utils/Rels";
import {shipTypesModelToMap} from "../../../Domain/games/ship/ShipType";
import {Orientation} from "../../../Domain/games/ship/Orientation";
import {useNavigationState} from "../../../Utils/NavigationStateProvider";


/**
 * Fetches a URL.
 *
 * @param url the URL to fetch
 * @return the response body
 */

/*function useFetch(url: string): string | undefined {
    const [data, setData] = useState(undefined);
    useEffect(() => {
        let cancelled = false;

        async function doFetch() {
            const res = await fetch(url);
            const body = await res.json();

            if (!cancelled)
                setData(body);
        }

        setData(undefined);
        doFetch();
        return () => {
            cancelled = true
        };
    }, [url]);

    return data;
}*/


/**
 * Gameplay component.
 */
function Gameplay() {
    const session = useSession();
    const location = useLocation();
    const [game, setGame] = React.useState<GetGameOutputModel | null>(null);
    const [myBoard, setMyBoard] = React.useState<Board | null>(null);
    const [opponentBoard, setOpponentBoard] = React.useState<Board | null>(null);
    const [error, setError] = React.useState<string | null>(null);
    const [battleshipsService, setBattleshipService] = useBattleshipsService()
    const navigate = useNavigate()
    const navigationState = useNavigationState();

    useEffect(() => {
        const fetchGame = async () => {
            if (!battleshipsService.links.get(Rels.GAME)) {
                navigate("/")
                return
            }

            const [err, res] = await to(
                battleshipsService.gamesService.getGame()
            );

            if (err) {
                handleError(err, setError);
                return;
            }

            if (res?.properties === undefined)
                throw new Error("Properties are undefined");

            const game = res.properties as GetGameOutputModel;

            setGame(game);
        }

        fetchGame();
    }, []);

    async function onBoardSetupFinished(board: Board) {
        const [err, res] = await to(battleshipsService.playersService.deployFleet({
            fleet: board.fleet.map(ship => {
                    return {
                        type: ship.type.shipName,
                        orientation: Orientation.toString(ship.orientation),
                        coordinate: {
                            col: ship.coordinate.col,
                            row: ship.coordinate.row
                        }
                    }
                }
            )
        }))

        if (err) {
            handleError(err, setError);
            return;
        }

        //wait for opponent
        await waitForOpponentToDeployFleet();
    }

    async function waitForOpponentToDeployFleet() {
        const [err, res] = await to(battleshipsService.gamesService.getGameState())

        if (err) {
            handleError(err, setError);
            return;
        }
        let waiting = true

        // TODO: cancel this wait
        while (!waiting) {
            const [err, res] = await to(
                battleshipsService.gamesService.getGameState()
            );

            if (err) {
                handleError(err, setError);
                return;
            }

            if (res?.properties === undefined)
                throw new Error("Entities are undefined");

            if (res.properties.phase === "DEPLOYING_FLEETS")
                await new Promise(resolve => setTimeout(resolve, 1000));
            else {
                waiting = false
            }
        }

    }

    if (game?.state.phase === "DEPLOYING_FLEETS")
        return (
            <BoardSetup boardSize={game.config.gridSize} ships={shipTypesModelToMap(game.config.shipTypes)}
                        onBoardReady={onBoardSetupFinished}/>
        )
    else if (game?.state.phase === "IN_PROGRESS")
        return (
            <PageContent error={error}>
                <Grid container spacing={3}>
                    <Grid item lg={4} md={6} xs={12}>
                        <Card>
                            <CardContent>
                                <Box
                                    sx={{
                                        alignItems: 'center',
                                        display: 'flex',
                                        flexDirection: 'row'
                                    }}
                                >
                                    <BoardView board={myBoard!}/>
                                </Box>
                            </CardContent>
                            <Divider/>
                            <CardActions>
                                <Button color="primary" fullWidth onClick={() => {
                                }}>
                                    Shoot
                                </Button>
                                <Button color="primary" fullWidth onClick={() => {
                                }}>
                                    Reset Shots
                                </Button>
                            </CardActions>
                        </Card>
                    </Grid>
                    <Grid item lg={8} md={6} xs={12}>
                        <BoardView board={opponentBoard!}/>
                    </Grid>
                </Grid>
            </PageContent>
        );
    else if (game?.state.phase === "FINISHED")
        return <div>Game finished</div>; // TODO: Implement game finished page
    else
        return (
            <PageContent error={error}>
                <LoadingSpinner text={"Loading game..."}/>
            </PageContent>
        );
}

export default Gameplay;
