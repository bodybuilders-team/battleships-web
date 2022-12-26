import * as React from "react";
import {useState} from "react";
import {defaultShipTypes, ShipType} from "../../../Domain/games/ship/ShipType";
import {defaultBoardSize} from "../../../Domain/games/board/Board";
import to from "../../../Utils/await-to";
import {handleError} from "../../../Services/utils/fetchSiren";
import {useNavigate} from "react-router-dom";
import PageContent from "../../Shared/PageContent";
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService";
import {useInterval} from "../Shared/TimersHooks/useInterval";
import LoadingSpinner from "../../Shared/LoadingSpinner";
import GameConfig from "./GameConfig";

const POLLING_DELAY = 1000;

/**
 * CreateGame component.
 */
export default function CreateGame() {
    const navigate = useNavigate();

    const battleshipsService = useBattleshipsService();

    const [gameName, setGameName] = useState("Game");
    const [gridSize, setGridSize] = useState(defaultBoardSize);
    const [maxTimePerRound, setMaxTimePerRound] = useState(100);
    const [shotsPerRound, setShotsPerRound] = useState(1);
    const [maxTimeForLayoutPhase, setMaxTimeForLayoutPhase] = useState(100);
    const [shipTypes, setShipTypes] = useState<Map<ShipType, number>>(defaultShipTypes);

    const [isWaitingForOpponent, setWaitingForOpponent] = useState<boolean>(false);
    const [gameId, setGameId] = useState<number | null>(null);
    const [error, setError] = useState<string | null>(null);

    useInterval(checkIfOpponentHasConnected, POLLING_DELAY, [isWaitingForOpponent]);

    /**
     * Handles the creation of a game.
     */
    function handleCreateGame() {
        async function createGame() {
            const [err, res] = await to(
                battleshipsService.gamesService.createGame(
                    {
                        name: gameName,
                        config: {
                            gridSize,
                            maxTimePerRound,
                            shotsPerRound,
                            maxTimeForLayoutPhase,
                            shipTypes: Array.from(shipTypes.entries()).map(([shipType, count]) => ({
                                shipName: shipType.shipName,
                                size: shipType.size,
                                quantity: count,
                                points: shipType.points
                            }))
                        }
                    }
                )
            );

            if (err) {
                handleError(err, setError);
                return;
            }

            setGameId(res.properties!.gameId);
            setWaitingForOpponent(true);
        }

        createGame();
    }

    /**
     * Checks if the opponent has connected.
     */
    async function checkIfOpponentHasConnected() {
        if (!isWaitingForOpponent)
            return true;

        const [err, res] = await to(
            battleshipsService.gamesService.getGameState()
        );

        if (err) {
            handleError(err, setError);
            return true;
        }

        if (res.properties!.phase !== "WAITING_FOR_PLAYERS") {
            setWaitingForOpponent(false);
            navigate(`/game/${gameId}`);
            return true;
        }

        return false;
    }


    if (isWaitingForOpponent)
        return (
            <PageContent error={error}>
                <LoadingSpinner text={"Waiting for opponent..."}/>
            </PageContent>
        );
    else
        return (
            <GameConfig
                setGameName={setGameName}
                gridSize={gridSize} setGridSize={setGridSize}
                maxTimePerRound={maxTimePerRound} setMaxTimePerRound={setMaxTimePerRound}
                shotsPerRound={shotsPerRound} setShotsPerRound={setShotsPerRound}
                maxTimeForLayoutPhase={maxTimeForLayoutPhase}
                setMaxTimeForLayoutPhase={setMaxTimeForLayoutPhase}
                shipTypes={shipTypes} setShipTypes={setShipTypes}
                handleCreateGame={handleCreateGame}
                error={error}
            />
        );
}
