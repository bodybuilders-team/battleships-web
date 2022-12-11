import {Game} from "../../../../Domain/games/game/Game";
import {Ship} from "../../../../Domain/games/ship/Ship";
import {useBattleshipsService} from "../../../../Services/NavigationBattleshipsService";
import {useSession} from "../../../../Utils/Session";
import {useState} from "react";
import {GameState} from "../../../../Domain/games/game/GameState";
import {MyBoard} from "../../../../Domain/games/board/MyBoard";
import {OpponentBoard} from "../../../../Domain/games/board/OpponentBoard";
import {useInterval} from "../../../Shared/useInterval";
import to from "../../../../Utils/await-to";
import {useTimeout} from "../../../Shared/useTimeout";
import {Coordinate} from "../../../../Domain/games/Coordinate";
import {throwError} from "../../../../Services/utils/errorUtils";
import {FiredShot} from "../../../../Domain/games/shot/FiredShot";

const TURN_SWITCH_DELAY = 1000;
const POLLING_DELAY = 1000;

/**
 * Hook for shooting.
 *
 * @param game game where the shooting is happening
 * @param myFleet fleet of the player
 * @param onError callback for errors
 */
export default function useShooting(game: Game, myFleet: Ship[], onError: (error: Error) => void) {
    const battleshipsService = useBattleshipsService();
    const session = useSession();

    // TODO: Combine state into one object, to do this we need to separate api calls from state updates
    // Maybe we can do this with a redux?
    const [gameState, setGameState] = useState<GameState>(game.state);
    const [myBoard, setMyBoard] = useState<MyBoard>(MyBoard.fromFleet(game.config.gridSize, myFleet));
    const [opponentBoard, setOpponentBoard] = useState<OpponentBoard>(new OpponentBoard(game.config.gridSize));
    const [winner, setWinner] = useState<string | null>(null);
    const [finished, setFinished] = useState<boolean>(false);
    const [myTurn, setMyTurn] = useState<boolean>(game.state.turn == session!.username);
    const [switchTurnWithDelay, setSwitchTurnWithDelay] = useState<boolean>(false);

    useInterval(async () => {
        if (myTurn)
            return true;

        const [err, res] = await to(battleshipsService.gamesService.getGameState());

        if (err) {
            onError(err);
            return true;
        }

        const gameState = new GameState(res?.properties!);

        if (gameState.phase === "FINISHED") {
            setFinished(true);
            return true;
        }

        setGameState(gameState);

        if (gameState.turn === session!.username) {
            const opponentShots = await getOpponentShots();
            if (opponentShots == null)
                return true;

            setMyBoard(MyBoard.fromFleet(myBoard.size, myBoard.fleet).shoot(opponentShots));
            setSwitchTurnWithDelay(true);
            return true;
        }

        return false;
    }, POLLING_DELAY, [myTurn]);

    useTimeout(() => {
        if (!switchTurnWithDelay)
            return;

        setSwitchTurnWithDelay(false);
        setMyTurn(!myTurn);
    }, TURN_SWITCH_DELAY, [switchTurnWithDelay]);

    /**
     * Shoots at the opponent.
     *
     * @param shots shots to shoot
     */
    async function fire(shots: Coordinate[]) {
        const [err, res] = await to(battleshipsService.playersService.fireShots({
            shots: shots.map(shot => {
                return {coordinate: shot.toCoordinateModel()}
            })
        }));

        if (err) {
            onError(err);
            return;
        }

        const shipTypes = game.config.shipTypes ?? throwError("Ship types are missing");

        const firedShots = res?.properties?.shots.map((shot) => {
            return new FiredShot(shot, shipTypes);
        }) ?? throwError("No shots found");

        setOpponentBoard(opponentBoard.updateWith(firedShots));
        setSwitchTurnWithDelay(true);
    }

    /**
     * Gets the opponent shots.
     */
    async function getOpponentShots() {
        const [err, res] = await to(battleshipsService.playersService.getOpponentShots());

        if (err) {
            onError(err);
            return;
        }

        return res?.properties?.shots.map((shot) => {
            return Coordinate.fromCoordinateModel(shot.coordinate);
        }) ?? throwError("No shots found");
    }

    return {shootingState: {gameState, myBoard, opponentBoard, finished, myTurn}, fire};
}
