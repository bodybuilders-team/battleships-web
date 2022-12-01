import * as React from "react";
import {useEffect} from "react";
import {useLocation} from "react-router-dom";
import * as gamesService from "../../Services/games/GamesService";
import to from "../../Utils/await-to";
import {useSession} from "../../Utils/Session";
import {handleError} from "../../Services/utils/fetchSiren";
import {Alert} from "@mui/material";
import BoardSetup from "./BoardSetup/BoardSetup";
import {GetGameOutputModel} from "../../Services/games/models/games/getGame/GetGameOutput";
import LoadingSpinner from "../Utils/LoadingSpinner";


/**
 * Gameplay component.
 */
function Gameplay() {
    const session = useSession();
    const location = useLocation();
    const gameLink = location.state.gameLink;
    const [game, setGame] = React.useState<GetGameOutputModel | null>(null);
    const [error, setError] = React.useState<string | null>(null);

    useEffect(() => {
        if (!game) {
            const fetchGame = async () => {
                const [err, res] = await to(
                    gamesService.getGame(
                        session!.accessToken,
                        gameLink
                    )
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
        }
    });

    if (game?.state.phase === "DEPLOYING_FLEETS")
        return <BoardSetup boardSize={game.config.gridSize} ships={game.config.shipTypes}/>;
    else if (game?.state.phase === "IN_PROGRESS")
        return <div>Game in progress</div>; // TODO: Implement game in progress
    else if (game?.state.phase === "FINISHED")
        return <div>Game finished</div>; // TODO: Implement game finished page

    return (
        <div>
            {error && <Alert severity="error">{error}</Alert>}
            {
                !game
                    ? <LoadingSpinner text={"Loading game..."}/>
                    : <></>

            }
        </div>
    );
}

export default Gameplay;
