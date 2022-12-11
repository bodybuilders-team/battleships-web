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

/**
 * Gameplay component.
 */
export default function Gameplay() {
    const navigate = useNavigate();
    const navigationState = useNavigationState();

    const battleshipsService = useBattleshipsService();

    const [game, setGame] = useState<Game | null>(null);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchGame = async () => {
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

            navigationState.setLinks(battleshipsService.links);
            const newGame = new Game(res.properties as GetGameOutputModel);
            setGame(newGame);
        }
        fetchGame();
    }, []);

    if (game?.state.phase === "DEPLOYING_FLEETS")
        return (
            <BoardSetupGameplay
                gameConfig={game.config}
                onBoardSetupPhaseFinished={(newGameState) => {
                    setGame({...game, state: newGameState})
                }}
            />
        );
    else if (game?.state.phase === "IN_PROGRESS")
        return <ShootingGameplay game={game}/>;
    else if (game?.state.phase === "FINISHED")
        return <div>Game finished</div>;
    else
        return (
            <PageContent error={error}>
                <LoadingSpinner text={"Loading game..."}/>
            </PageContent>
        );
}
