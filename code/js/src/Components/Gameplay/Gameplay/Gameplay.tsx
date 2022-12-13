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
import BoardSetupGameplay from "../BoardSetup/BoardSetupGameplay";
import {Game} from "../../../Domain/games/game/Game";
import GameFinished from "../Shared/EndGame/GameFinished";
import {Uris} from "../../../Utils/navigation/Uris";
import HOME = Uris.HOME;

/**
 * Gameplay component.
 */
export default function Gameplay() {
    const navigate = useNavigate();
    const battleshipsService = useBattleshipsService();

    const [game, setGame] = useState<Game | null>(null);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetchGame();
    }, []);

    /**
     * Fetches the game.
     */
    async function fetchGame() {
        if (!battleshipsService.links.get(Rels.GAME)) {
            navigate(HOME);
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


    if (game?.state.phase === "DEPLOYING_FLEETS")
        return (
            <BoardSetupGameplay
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
