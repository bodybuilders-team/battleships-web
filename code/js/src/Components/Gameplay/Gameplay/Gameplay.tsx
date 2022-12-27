import * as React from "react";
import {useState} from "react";
import to from "../../../Utils/await-to";
import {handleError} from "../../../Services/utils/fetchSiren";
import {GetGameOutputModel} from "../../../Services/services/games/models/games/getGame/GetGameOutput";
import LoadingSpinner from "../../Shared/LoadingSpinner";
import PageContent from "../../Shared/PageContent";
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService";
import ShootingGameplay from "./Shooting/ShootingGameplay";
import BoardSetupGameplay from "./BoardSetup/BoardSetupGameplay";
import {Game} from "../../../Domain/games/game/Game";
import GameFinished from "../Shared/EndGame/GameFinished";
import {abortableTo, useAbortableEffect} from "../../../Utils/abortableUtils";

/**
 * Gameplay component.
 */
export default function Gameplay() {
    const battleshipsService = useBattleshipsService();

    const [game, setGame] = useState<Game | null>(null);
    const [error, setError] = useState<string | null>(null);

    useAbortableEffect(() => {
        fetchGame()
    }, []);

    /**
     * Fetches the game.
     */
    async function fetchGame() {
        const [err, res] = await abortableTo(
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


    if (game?.state.phase === "DEPLOYING_FLEETS")
        return (
            <BoardSetupGameplay
                finalTime={game.state.phaseEndTime}
                gameConfig={game!.config}
                onFinished={fetchGame}
            />
        );
    else if (game?.state.phase === "IN_PROGRESS")
        return <ShootingGameplay game={game!}/>;
    else if (game?.state.phase === "FINISHED")
        return <GameFinished game={game}/>;
    else
        return (
            <PageContent error={error}>
                <LoadingSpinner text={"Loading game..."}/>
            </PageContent>
        );
}
