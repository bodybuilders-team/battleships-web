import * as React from "react"
import {useEffect, useRef, useState} from "react"
import {defaultShipTypes, ShipType} from "../../../Domain/games/ship/ShipType"
import {defaultBoardSize} from "../../../Domain/games/board/Board"
import {handleError} from "../../../Services/utils/fetchSiren"
import {useNavigate} from "react-router-dom"
import PageContent from "../../Shared/PageContent"
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService"
import {useInterval} from "../Shared/TimersHooks/useInterval"
import LoadingSpinner from "../../Shared/LoadingSpinner"
import GameConfig from "./GameConfig"
import {abortableTo} from "../../../Utils/componentManagement/abortableUtils"
import {useMountedSignal} from "../../../Utils/componentManagement/useMounted"
import {useNavigationState} from "../../../Utils/navigation/NavigationState"

const POLLING_DELAY = 1000

/**
 * CreateGame component.
 */
export default function CreateGame() {
    const navigate = useNavigate()

    const battleshipsService = useBattleshipsService()

    const [gameName, setGameName] = useState("Game")
    const [gridSize, setGridSize] = useState(defaultBoardSize)
    const [maxTimePerRound, setMaxTimePerRound] = useState(100)
    const [shotsPerRound, setShotsPerRound] = useState(1)
    const [maxTimeForLayoutPhase, setMaxTimeForLayoutPhase] = useState(100)
    const [shipTypes, setShipTypes] = useState<Map<ShipType, number>>(defaultShipTypes)

    const [isWaitingForOpponent, setWaitingForOpponent] = useState<boolean>(false)
    const [gameId, setGameId] = useState<number | null>(null)
    const [error, setError] = useState<string | null>(null)

    useInterval(checkIfOpponentHasConnected, POLLING_DELAY, [isWaitingForOpponent])
    const mountedSignal = useMountedSignal()
    const navigatingToGameRef = useRef(false)
    const navigationState = useNavigationState()

    useEffect(() => {

        return () => {
            if (!navigatingToGameRef.current)
                navigationState.clearGameLinks()
        }
    }, [])

    /**
     * Handles the creation of a game.
     */
    function handleCreateGame() {
        async function createGame() {
            const [err, res] = await abortableTo(
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
                        },
                    },
                    mountedSignal
                )
            )

            if (err) {
                handleError(err, setError, navigate)
                return
            }

            setGameId(res.properties!.gameId)
            setWaitingForOpponent(true)
        }

        createGame()
    }

    /**
     * Checks if the opponent has connected.
     */
    async function checkIfOpponentHasConnected() {
        if (!isWaitingForOpponent)
            return true

        const [err, res] = await abortableTo(
            battleshipsService.gamesService.getGameState(mountedSignal)
        )

        if (err) {
            handleError(err, setError, navigate)
            return true
        }

        if (res.properties!.phase !== "WAITING_FOR_PLAYERS") {
            setWaitingForOpponent(false)
            navigatingToGameRef.current = true

            navigate(`/game/${gameId}`)
            return true
        }

        return false
    }


    if (isWaitingForOpponent)
        return (
            <PageContent error={error}>
                <LoadingSpinner text={"Waiting for opponent..."}/>
            </PageContent>
        )
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
        )
}
