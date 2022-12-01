import * as React from "react";
import {useEffect} from "react";
import Typography from "@mui/material/Typography";
import LoadingSpinner from "../../Utils/LoadingSpinner";
import to from "../../../Utils/await-to";
import * as gamesService from '../../../Services/games/GamesService';
import {useSession} from "../../../Utils/Session";
import {handleError} from "../../../Services/utils/fetchSiren";
import {useNavigate} from "react-router-dom";
import {EmbeddedLink} from "../../../Services/utils/siren/SubEntity";
import PageContent from "../../Utils/PageContent";

const defaultGameConfig = require('../../../Assets/defaultGameConfig.json');

/**
 * Matchmake component.
 */
function Matchmake() {

    const navigate = useNavigate();
    const session = useSession();
    const [matchmade, setMatchmade] = React.useState(false);
    const [error, setError] = React.useState<string | null>(null);

    useEffect(() => {
        if (!matchmade) {
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

                const gameStateLink = res.entities?.filter(e => e.rel.includes("game-state"))[0] as EmbeddedLink
                const gameLink = res.entities?.filter(e => e.rel.includes("game"))[0] as EmbeddedLink

                if (!res.properties.wasCreated) {
                    console.log("Matchmake by joining existing game");
                    setMatchmade(true);
                    navigate("/gameplay", {state: {gameLink: gameLink.href}});
                    return;
                }

                while (!matchmade) {
                    const [err, res] = await to(
                        gamesService.getGameState(
                            session!.accessToken, // TODO: handle undefined session
                            "http://localhost:8080" + gameStateLink.href
                        )
                    );

                    if (err) {
                        handleError(err, setError);
                        return;
                    }

                    if (res?.properties === undefined)
                        throw new Error("Entities are undefined");

                    if (res.properties.phase === "WAITING_FOR_PLAYERS")
                        await new Promise(resolve => setTimeout(resolve, 1000));
                    else {
                        console.log("Matchmade!");
                        setMatchmade(true);
                        navigate("/gameplay", {state: {gameLink: gameLink.href}});
                    }
                }
            }

            fetchMatchmake()
        }
    }, []);

    return (
        <PageContent title={"Matchmake"} error={error}>
            {
                matchmade
                    ? <Typography variant="h6">Matchamade!</Typography>
                    : <LoadingSpinner text={"Matchmaking..."}/>
            }
        </PageContent>
    );
}

export default Matchmake;
