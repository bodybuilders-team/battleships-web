import * as React from "react";
import {useEffect} from "react";
import Typography from "@mui/material/Typography";
import LoadingSpinner from "../../Utils/LoadingSpinner";
import {Alert} from "@mui/material";
import to from "../../../Utils/await-to";
import * as gamesService from '../../../Services/games/GamesService';
import {useSession} from "../../../Utils/Session";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import {handleError} from "../../../Services/utils/fetchSiren";
import {useNavigate} from "react-router-dom";
import {EmbeddedLink} from "../../../Services/utils/siren/SubEntity";

const defaultGameConfig = require('../../../Assets/defaultGameConfig.json');

/**
 * Matchmake component.
 */
function Matchmake() {

    const navigate = useNavigate();
    const session = useSession();
    const [matchmake, setMatchmake] = React.useState(false);
    const [error, setError] = React.useState<string | null>(null);

    const [gameLink, setGameLink] = React.useState<EmbeddedLink | null>(null);

    useEffect(() => {
        if (!matchmake) {
            const fetchMatchmake = async () => {
                const [err, res] = await to(
                    gamesService.matchmake(
                        session!.accessToken, // TODO: handle undefined session
                        "http://localhost:8080/games/matchmake",
                        defaultGameConfig
                    )
                );

                if (err) {
                    handleError(err, setError);
                    return;
                }

                if (res?.properties === undefined)
                    throw new Error("Entities are undefined");

                if (!res.properties.wasCreated) {
                    setMatchmake(true);
                    setGameLink(res.entities?.filter(e => e.rel.includes("game"))[0] as EmbeddedLink);
                    navigate("/gameplay", {state: {gameLink: gameLink}});
                    return;
                }

                while (true) {
                    console.log("Waiting for game to start...");
                    const [err, res] = await to(
                        gamesService.getGameState(
                            session!.accessToken, // TODO: handle undefined session
                            gameLink!.href
                        )
                    );

                    if (err) {
                        handleError(err, setError);
                        return;
                    }

                    if (res?.properties === undefined)
                        throw new Error("Entities are undefined");

                    if (res.properties.phase === "WAITING_FOR_PLAYERS_PHASE")
                        await new Promise(resolve => setTimeout(resolve, 1000));
                    else {
                        setMatchmake(true);
                        navigate("/gameplay", {state: {gameLink: gameLink}});
                        break;
                    }

                }
                setMatchmake(true);
            }

            fetchMatchmake();
        }
    });

    return (
        <Container component="main" maxWidth="xs">
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                {error && <Alert severity="error">{error}</Alert>}
                {
                    matchmake
                        ? <Typography variant="h6">Matchamade!</Typography>
                        : <LoadingSpinner text={"Matchmaking..."}/>
                }
            </Box>
        </Container>
    );
}

export default Matchmake;
