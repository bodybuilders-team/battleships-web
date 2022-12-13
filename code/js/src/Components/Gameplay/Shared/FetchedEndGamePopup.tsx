import * as React from "react";
import {useState} from "react";
import {Game} from "../../../Domain/games/game/Game";
import {useInterval} from "./useInterval";
import {Rels} from "../../../Utils/navigation/Rels";
import to from "../../../Utils/await-to";
import {GetGameOutputModel} from "../../../Services/services/games/models/games/getGame/GetGameOutput";
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService";
import {useNavigate} from "react-router-dom";
import EndGamePopup, {EndGameCause, WinningPlayer} from "./EndGamePopup";
import {useSession} from "../../../Utils/Session";

/**
 * Props for FetchedEndGamePopup.
 *
 * @property open whether the popup is open
 * @property onError callback for when an error occurs fetching
 */
interface FetchedEndGamePopupProps {
    open: boolean,
    onError: (err: Error) => void
}

const POLLING_DELAY = 1000;

/**
 * EndGamePopup that fetches the game.
 */
function FetchedEndGamePopup({open, onError}: FetchedEndGamePopupProps) {
    const [game, setGame] = useState<Game | null>(null);
    const battleshipsService = useBattleshipsService();
    const navigate = useNavigate()

    useInterval(fetchGame, POLLING_DELAY, [open]);

    async function fetchGame() {
        if (!open) return false;

        if (!battleshipsService.links.get(Rels.GAME)) {
            navigate("/");
            return true;
        }

        const [err, res] = await to(
            battleshipsService.gamesService.getGame()
        );

        if (err) {
            onError(err);
            return true;
        }

        if (res?.properties === undefined)
            throw new Error("Properties are undefined");

        if (res.properties.state.phase === "FINISHED") {
            const newGame = new Game(res.properties as GetGameOutputModel);
            setGame(newGame);
            return true
        }

        return false
    }

    if (game != null) {
        const session = useSession()
        const username = session!.username

        const player = game.getPlayer(username)!
        const opponent = game.getOpponent(username)!
        console.log(game.state.winner)

        return <EndGamePopup
            open={game?.state.phase === "FINISHED"}
            winningPlayer={
                game.state.winner == null ? WinningPlayer.NONE :
                    game.state.winner === session?.username!
                        ? WinningPlayer.YOU : WinningPlayer.OPPONENT
            } cause={(() => {
            switch (game.state.endCause) {
                case "DESTRUCTION":
                    return EndGameCause.DESTRUCTION
                case "TIMEOUT":
                    return EndGameCause.TIMEOUT
                default:
                    return EndGameCause.RESIGNATION
            }
        })()}
            playerInfo={{
                name: player.username,
                points: player.points
            }}
            opponentInfo={{
                name: opponent.username,
                points: opponent.points
            }}
        />
    } else
        return null
}

export default FetchedEndGamePopup;