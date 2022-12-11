import BoardSetup from "./BoardSetup";
import * as React from "react";
import {useEffect, useState} from "react";
import {Board} from "../../../Domain/games/board/Board";
import to from "../../../Utils/await-to";
import {Orientation} from "../../../Domain/games/ship/Orientation";
import {handleError} from "../../../Services/utils/fetchSiren";
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService";
import LoadingSpinner from "../../Shared/LoadingSpinner";
import PageContent from "../../Shared/PageContent";
import {useInterval} from "../../Shared/useInterval";
import {GameConfig} from "../../../Domain/games/game/GameConfig";
import {GameState} from "../../../Domain/games/game/GameState";

/**
 * Properties for BoardSetupGameplay component.
 *
 * @property gameConfig the game config
 * @property onBoardSetupPhaseFinished callback when the board setup phase is finished
 */
interface BoardSetupGameplayProps {
    gameConfig: GameConfig;
    onBoardSetupPhaseFinished: (gameState: GameState) => void;
}

const pollingDelay = 1000;

/**
 * BoardSetupGameplay component.
 */
export default function BoardSetupGameplay({gameConfig, onBoardSetupPhaseFinished}: BoardSetupGameplayProps) {
    const battleshipsService = useBattleshipsService();
    const [isWaitingForOpponent, setWaitingForOpponent] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);

    const [fetchingGameState, setFetchingGameState] = useState<boolean>(true);
    const [finalTime, setFinalTime] = useState<number | null>(null);

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
            handleError(err, setError);
            return;
        }

        setWaitingForOpponent(true);
    }

    useInterval(async () => {
        if (!isWaitingForOpponent)
            return true;

        const [err, res] = await to(
            battleshipsService.gamesService.getGameState()
        );

        if (err) {
            handleError(err, setError);
            return true;
        }

        if (res.properties!.phase !== "DEPLOYING_FLEETS") {
            setWaitingForOpponent(false);
            onBoardSetupPhaseFinished(new GameState(res.properties!));
            return true;
        }

        return false;
    }, pollingDelay, [isWaitingForOpponent]);

    useEffect(() => {
        if (!fetchingGameState)
            return;

        async function fetchGameState() {
            const [err, res] = await to(
                battleshipsService.gamesService.getGameState()
            );

            if (err) {
                handleError(err, setError);
                return;
            }

            setFinalTime(res.properties!.phaseEndTime);
            setFetchingGameState(false);
        }

        fetchGameState();
    }, [fetchingGameState]);


    if (isWaitingForOpponent)
        return (
            <PageContent error={error}>
                <LoadingSpinner text={"Waiting for opponent to deploy his fleet..."}/>
            </PageContent>
        );
    else if (fetchingGameState)
        return (
            <PageContent error={error}>
                <LoadingSpinner text={"Loading game state..."}/>
            </PageContent>
        );
    else
        return (
            <BoardSetup
                finalTime={finalTime!}
                boardSize={gameConfig.gridSize} ships={gameConfig.shipTypes}
                onBoardReady={onBoardSetupFinished}
            />
        );
}
