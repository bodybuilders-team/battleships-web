import BoardSetup from "./BoardSetup";
import * as React from "react";
import {useEffect} from "react";
import {GameConfig, GameState} from "../Gameplay/Gameplay";
import {Board} from "../../../Domain/games/board/Board";
import to from "../../../Utils/await-to";
import {Orientation} from "../../../Domain/games/ship/Orientation";
import {handleError} from "../../../Services/utils/fetchSiren";
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService";
import LoadingSpinner from "../../Utils/LoadingSpinner";
import PageContent from "../../Utils/PageContent";
import {useInterval} from "../../../Hooks/useInterval";

interface BoardSetupHandlerProps {
    gameConfig: GameConfig;
    onBoardSetupPhaseFinished: (gameState: GameState) => void;
}

const POLLING_DELAY = 1000;


function BoardSetupGameplay({gameConfig, onBoardSetupPhaseFinished}: BoardSetupHandlerProps) {
    const battleshipsService = useBattleshipsService();
    const [isWaitingForOpponent, setWaitingForOpponent] = React.useState<boolean>(false);
    const [error, setError] = React.useState<string | null>(null);

    const [fetchingGameState, setFetchingGameState] = React.useState<boolean>(true);
    const [finalTime, setFinalTime] = React.useState<number | null>(null);


    async function onBoardSetupFinished(board: Board) {
        const [err, res] = await to(battleshipsService.playersService.deployFleet({
            fleet: board.fleet.map(ship => {
                    return {
                        type: ship.type.shipName,
                        orientation: Orientation.toString(ship.orientation),
                        coordinate: ship.coordinate.toCoordinateModel()
                    }
                }
            )
        }))

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
            onBoardSetupPhaseFinished(new GameState(res.properties!));
            setWaitingForOpponent(false);
            return true
        }

        return false
    }, POLLING_DELAY, [isWaitingForOpponent]);

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
    }, [fetchingGameState])


    if (isWaitingForOpponent)
        return <PageContent error={error}>
            <LoadingSpinner text={"Waiting for opponent to deploy his fleet..."}/>
        </PageContent>
    else if (fetchingGameState)
        return <PageContent error={error}>
            <LoadingSpinner text={"Loading game state..."}/>
        </PageContent>
    else
        return (
            <BoardSetup finalTime={finalTime!} boardSize={gameConfig.gridSize} ships={gameConfig.shipTypes}
                        onBoardReady={onBoardSetupFinished}/>
        )
}

export default BoardSetupGameplay