import * as React from "react";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import to from "../../../Utils/await-to";
import {handleError} from "../../../Services/utils/fetchSiren";
import {GetGameOutputModel} from "../../../Services/services/games/models/games/getGame/GetGameOutput";
import LoadingSpinner from "../../Shared/LoadingSpinner";
import PageContent from "../../Shared/PageContent";
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService";
import {Rels} from "../../../Utils/navigation/Rels";
import ShootingGameplay from "./Shooting/ShootingGameplay";
import {useNavigationState} from "../../../Utils/navigation/NavigationState";
import BoardSetupGameplay from "../BoardSetup/BoardSetupGameplay";
import {Game} from "../../../Domain/games/game/Game";
import EndGamePopup, {EndGameCause, WinningPlayer} from "../Shared/EndGamePopup";
import {useSession} from "../../../Utils/Session";
import Box from "@mui/material/Box";

/**
 * Gameplay component.
 */
export default function Gameplay() {
    const navigate = useNavigate();
    const session = useSession()
    const battleshipsService = useBattleshipsService();

    const [lastGamePhase, setLastGamePhase] = useState<string | null>(null);
    const [game, setGame] = useState<Game | null>(null);
    const [error, setError] = useState<string | null>(null);

    const fetchGame = async () => {
        if (game !== null)
            setLastGamePhase(game.state.phase);

        if (!battleshipsService.links.get(Rels.GAME)) {
            navigate("/");
            return;
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

        const newGame = new Game(res.properties as GetGameOutputModel);
        setGame(newGame);
    }

    useEffect(() => {
        fetchGame();
    }, []);

    if (game == null)
        return (
            <PageContent error={error}>
                <LoadingSpinner text={"Loading game..."}/>
            </PageContent>
        );

    function CurrentPhaseComponent() {
        if (game?.state.phase === "DEPLOYING_FLEETS" ||
            (lastGamePhase === "DEPLOYING_FLEETS" && game?.state.phase === "FINISHED"))
            return (
                <BoardSetupGameplay
                    gameConfig={game!.config}
                    onFinished={() => {
                        fetchGame()
                    }}
                />
            );
        else if (game?.state.phase === "IN_PROGRESS" ||
            (lastGamePhase === "IN_PROGRESS" && game?.state.phase === "FINISHED"))
            return <ShootingGameplay game={game!} onFinished={() => {
                fetchGame()
            }
            }/>;

        return null
    }

    const username = session!.username

    const player = game.getPlayer(username)!
    const opponent = game.getOpponent(username)!

    // TODO: Make sure parent gameplay component does not rerender so that the background does not change
    //  that or change the whole flow so that the background is not changed
    return <Box>
        <CurrentPhaseComponent/>
        <EndGamePopup open={game?.state.phase === "FINISHED"} winningPlayer={
            game.state.winner === session?.username!
                ? WinningPlayer.YOU : WinningPlayer.OPPONENT
        } cause={
            // TODO: add smthg like this. game.state.cause
            EndGameCause.DESTRUCTION
        } // Fix cause
                      playerInfo={{
                          name: player.username,
                          points: player.points
                      }}
                      opponentInfo={{
                          name: opponent.username,
                          points: opponent.points
                      }}
        />
    </Box>
}
