import BoardSetup from "./BoardSetup";
import * as React from "react";
import {useState} from "react";
import {Board} from "../../../../Domain/games/board/Board";
import to from "../../../../Utils/await-to";
import {Orientation} from "../../../../Domain/games/ship/Orientation";
import {handleError} from "../../../../Services/utils/fetchSiren";
import {useBattleshipsService} from "../../../../Services/NavigationBattleshipsService";
import LoadingSpinner from "../../../Shared/LoadingSpinner";
import PageContent from "../../../Shared/PageContent";
import {useInterval} from "../../Shared/TimersHooks/useInterval";
import {GameConfig} from "../../../../Domain/games/game/GameConfig";
import {useNavigate} from "react-router-dom";
import {Problem} from "../../../../Services/media/Problem";
import {ProblemTypes} from "../../../../Utils/types/problemTypes";
import FetchedEndGamePopup from "../../Shared/EndGame/FetchedEndGamePopup";
import {Uris} from "../../../../Utils/navigation/Uris";
import GAMEPLAY_MENU = Uris.GAMEPLAY_MENU;

/**
 * Properties for BoardSetupGameplay component.
 *
 * @property finalTime the time of the end of the phase
 * @property gameConfig the game config
 * @property onFinished callback when the board setup phase is finished
 */
interface BoardSetupGameplayProps {
    finalTime: number;
    gameConfig: GameConfig;
    onFinished: () => void;
}

const POLLING_DELAY = 1000;

/**
 * BoardSetupGameplay component.
 */
export default function BoardSetupGameplay({finalTime, gameConfig, onFinished}: BoardSetupGameplayProps) {
    const battleshipsService = useBattleshipsService();
    const [isWaitingForOpponent, setWaitingForOpponent] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const [showEndGamePopup, setShowEndGamePopup] = useState<boolean>(false);

    const navigate = useNavigate();

    useInterval(() => {
        fetchGameState()
    }, POLLING_DELAY, [isWaitingForOpponent]);

    /**
     * Callback when the board setup phase is finished.
     *
     * @param board the board
     */
    async function onBoardSetupFinished(board: Board) {
        const [err] = await to(battleshipsService.playersService.deployFleet({
            fleet: board.fleet.map(ship => {
                    return {
                        type: ship.type.shipName,
                        orientation: Orientation.toString(ship.orientation),
                        coordinate: ship.coordinate.toCoordinateModel()
                    }
                }
            )
        }));

        if (err) {
            if (err instanceof Problem && err.type === ProblemTypes.INVALID_PHASE) {
                setShowEndGamePopup(true);
                return;
            }

            handleError(err, setError);
            return;
        }

        setWaitingForOpponent(true);
    }

    /**
     * Fetches the game state.
     */
    async function fetchGameState() {
        const [err, res] = await to(
            battleshipsService.gamesService.getGameState()
        );

        if (err) {
            handleError(err, setError);
            return false;
        }

        if (isWaitingForOpponent && res.properties!.phase == "IN_PROGRESS") {
            setWaitingForOpponent(false);
            onFinished();
            return true;
        } else if (res?.properties?.phase == "FINISHED") {
            setShowEndGamePopup(true);
            return true;
        }

        return false;
    }


    /**
     * Callback when the user wants to leave the game.
     */
    async function leaveGame() {
        const [err] = await to(battleshipsService.gamesService.leaveGame());

        if (err) {
            handleError(err, setError);
            return;
        }

        navigate(GAMEPLAY_MENU);
    }


    if (isWaitingForOpponent)
        return (
            <>
                <PageContent error={error}>
                    <LoadingSpinner text={"Waiting for opponent to deploy his fleet..."}/>
                </PageContent>
                <FetchedEndGamePopup open={showEndGamePopup} onError={(err) => {
                    handleError(err, setError);
                }}/>
            </>
        );
    else return (
        <>
            <BoardSetup
                finalTime={finalTime}
                boardSize={gameConfig.gridSize} ships={gameConfig.shipTypes}
                onBoardReady={onBoardSetupFinished}
                onLeaveGame={leaveGame}
                error={error}
                onTimeUp={() => {
                    setShowEndGamePopup(true);
                }}
            />
            <FetchedEndGamePopup open={showEndGamePopup} onError={(err) => {
                handleError(err, setError);
            }}/>
        </>
    );
}
