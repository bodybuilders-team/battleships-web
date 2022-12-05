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
    const [battleshipService, setBattleshipService] = useBattleshipsService()
    const navigate = useNavigate()

    useEffect(() => {
        const fetchGame = async () => {
            if (!battleshipService.links.get(Rels.GAME)) {
                navigate("/")
                return
            }

            const [err, res] = await to(
                battleshipService.gamesService.getGame()
            );

            if (err) {
                handleError(err, setError);
                return;
            }

            if (res?.properties === undefined)
                throw new Error("Properties are undefined");

            const game = res.properties as GetGameOutputModel;
            console.log(game);
            setGame(game);
        }

        fetchGame();
    }, []);

    if (game?.state.phase === "DEPLOYING_FLEETS")
        return (
            <BoardSetup boardSize={game.config.gridSize} ships={game.config.shipTypes}/>
        );
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
                                    <BoardView size={game.config.gridSize} grid={myBoard!.grid}/>
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
                        <BoardView size={game.config.gridSize} grid={opponentBoard!.grid}/>
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
