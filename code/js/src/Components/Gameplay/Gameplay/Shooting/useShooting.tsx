import {Game} from "../../../../Domain/games/game/Game"
import {Ship} from "../../../../Domain/games/ship/Ship"
import {useBattleshipsService} from "../../../../Services/NavigationBattleshipsService"
import {useSession} from "../../../../Utils/Session"
import {useState} from "react"
import {GameState} from "../../../../Domain/games/game/GameState"
import {MyBoard} from "../../../../Domain/games/board/MyBoard"
import {OpponentBoard} from "../../../../Domain/games/board/OpponentBoard"
import {useInterval} from "../../Shared/TimersHooks/useInterval"
import {useTimeout} from "../../Shared/TimersHooks/useTimeout"
import {Coordinate} from "../../../../Domain/games/Coordinate"
import {throwError} from "../../../../Services/utils/errorUtils"
import {FiredShot} from "../../../../Domain/games/shot/FiredShot"
import {Problem} from "../../../../Services/media/Problem"
import {ProblemTypes} from "../../../../Utils/types/problemTypes"
import {useMountedSignal} from "../../../../Utils/componentManagement/useMounted"
import {abortableTo} from "../../../../Utils/componentManagement/abortableUtils"

const TURN_SWITCH_DELAY = 1000
const POLLING_DELAY = 1000

/**
 * Hook for shooting.
 *
 * @param game game where the shooting is happening
 * @param myFleet fleet of the player
 * @param onError callback for errors
 */
export default function useShooting(game: Game, myFleet: Ship[], onError: (error: Error) => void) {
    const battleshipsService = useBattleshipsService()
    const session = useSession()

    const [gameState, setGameState] = useState<GameState>(game.state)
    const [myBoard, setMyBoard] = useState<MyBoard>(MyBoard.fromFleet(game.config.gridSize, myFleet))
    const [opponentBoard, setOpponentBoard] = useState<OpponentBoard>(new OpponentBoard(game.config.gridSize))
    const [finished, setFinished] = useState<boolean>(false)
    const [myTurn, setMyTurn] = useState<boolean>(game.state.turn == session!.username)
    const [switchTurnWithDelay, setSwitchTurnWithDelay] = useState<boolean>(false)

    useInterval(checkIfOpponentHasFinishedHisTurn, POLLING_DELAY, [myTurn])

    useTimeout(switchTurn, TURN_SWITCH_DELAY, [switchTurnWithDelay])

    const mountedSignal = useMountedSignal()

    /**
     * Checks if the opponent has finished his turn.
     */
    async function checkIfOpponentHasFinishedHisTurn() {
        if (myTurn)
            return true

        const [err, res] = await abortableTo(battleshipsService.gamesService.getGameState(mountedSignal))

        if (err) {
            onError(err)
            return true
        }

        const gameState = new GameState(res?.properties!)

        if (gameState.phase === "FINISHED") {
            setFinished(true)
            return true
        }

        setGameState(gameState)

        if (gameState.turn === session!.username) {
            const opponentShots = await getOpponentShots()
            if (opponentShots == null)
                return true

            setMyBoard(MyBoard.fromFleet(myBoard.size, myBoard.fleet).shoot(opponentShots))
            setSwitchTurnWithDelay(true)
            return true
        }

        return false
    }

    /**
     * Switches the turn.
     */
    function switchTurn() {
        if (!switchTurnWithDelay)
            return

        setSwitchTurnWithDelay(false)
        setMyTurn(!myTurn)
    }


    /**
     * Shoots at the opponent.
     *
     * @param shots shots to shoot
     */
    async function fire(shots: Coordinate[]) {
        const [err, res] = await abortableTo(battleshipsService.playersService.fireShots({
            shots: shots.map(shot => {
                return {coordinate: shot.toCoordinateModel()}
            })
        }, mountedSignal))


        if (err) {
            if (err instanceof Problem && err.type === ProblemTypes.INVALID_PHASE) {
                setFinished(true)
                return
            }
            onError(err)
            return
        }

        const shipTypes = game.config.shipTypes ?? throwError("Ship types are missing")

        const firedShots = res?.properties?.shots.map((shot) => {
            return new FiredShot(shot, shipTypes)
        }) ?? throwError("No shots found")

        setOpponentBoard(opponentBoard.updateWith(firedShots))
        setSwitchTurnWithDelay(true)
    }

    /**
     * Gets the opponent shots.
     */
    async function getOpponentShots() {
        const [err, res] = await abortableTo(battleshipsService
            .playersService.getOpponentShots(mountedSignal))

        if (err) {
            onError(err)
            return null
        }

        return res?.properties?.shots.map((shot) => {
            return Coordinate.fromCoordinateModel(shot.coordinate)
        }) ?? throwError("No shots found")
    }

    return {
        shootingState: {gameState, myBoard, opponentBoard, finished, myTurn},
        fire
    }
}
