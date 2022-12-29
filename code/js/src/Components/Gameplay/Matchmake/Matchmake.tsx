import * as React from "react"
import {useState} from "react"
import Typography from "@mui/material/Typography"
import LoadingSpinner from "../../Shared/LoadingSpinner"
import to from "../../../Utils/await-to"
import {handleError} from "../../../Services/utils/fetchSiren"
import {useNavigate} from "react-router-dom"
import PageContent from "../../Shared/PageContent"
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService"
import {useInterval} from "../Shared/TimersHooks/useInterval"
import {useSession} from "../../../Utils/Session"
import {GetGameOutputModel} from "../../../Services/services/games/models/games/getGame/GetGameOutput"
import {Game} from "../../../Domain/games/game/Game"
import {useNavigationState} from "../../../Utils/navigation/NavigationState"
import {Rels} from "../../../Utils/navigation/Rels"
import {abortableTo, useAbortableEffect} from "../../../Utils/abortableUtils"

const defaultGameConfig = require('../../../Assets/defaultGameConfig.json')
const POLLING_DELAY = 1000

/**
 * Matchmake component.
 */
export default function Matchmake() {
    const navigate = useNavigate()
    const battleshipsService = useBattleshipsService()

    const [error, setError] = useState<string | null>(null)
    const [matchmade, setMatchmade] = useState(false)
    const [isWaitingForOpponent, setWaitingForOpponent] = useState<boolean>(false)
    const [gameId, setGameId] = useState<number | null>(null)
    const session = useSession()

    const navigationState = useNavigationState()
    useAbortableEffect(() => {
        matchmake()

        return () => {
            navigationState.links.delete(Rels.GAME)
        }
    }, [])

    useInterval(checkIfOpponentJoined, POLLING_DELAY, [isWaitingForOpponent])

    /**
     * Checks if there's an available game.
     *
     */
    async function checkIfThereIsAnAvailableGame() {
        const [err, res] = await abortableTo(battleshipsService.gamesService.getGames({
            username: session!.username,
            phases: ["WAITING_FOR_PLAYERS"]
        }))
        console.log(navigationState)

        if (err) {
            handleError(err, setError)
            return false
        }

        const games = res.getEmbeddedSubEntities<GetGameOutputModel>()

        if (games.length > 0) {
            const game = new Game(games[0].properties!)

            navigationState.links.set(Rels.GAME, navigationState.links.get(`${Rels.GAME}-${game.id}`)!)
            navigationState.links.set(Rels.GAME_STATE, navigationState.links.get(`${Rels.GAME_STATE}-${game.id}`)!)

            setGameId(game.id)
            setWaitingForOpponent(true)
            return true
        }

        return false
    }

    /**
     * Matchmakes the player.
     */
    async function matchmake() {
        if (await checkIfThereIsAnAvailableGame() == true)
            return

        const [err, res] = await abortableTo(
            battleshipsService.gamesService.matchmake(defaultGameConfig)
        )

        if (err) {
            handleError(err, setError)
            return
        }

        const gameId = res.properties!.gameId
        setGameId(gameId)

        if (!res.properties!.wasCreated) {
            setMatchmade(true)
            navigate(`/game/${gameId}`)
            return
        }

        setWaitingForOpponent(true)
    }

    /**
     * Checks if the opponent has joined the game.
     *
     * @returns true if the opponent has joined, false otherwise
     */
    async function checkIfOpponentJoined() {
        if (!isWaitingForOpponent)
            return true

        const [err, res] = await abortableTo(
            battleshipsService.gamesService.getGameState()
        )
        console.log(battleshipsService.links)
        if (err) {
            handleError(err, setError)
            return true
        }

        if (res.properties!.phase !== "WAITING_FOR_PLAYERS") {
            setMatchmade(true)
            setWaitingForOpponent(false)
            navigate(`/game/${gameId}`)
            return true
        }

        return false
    }

    return (
        <PageContent title={"Matchmake"} error={error}>
            {
                matchmade
                    ? <Typography variant="h6">Matchmade!</Typography>
                    : <LoadingSpinner text={"Matchmaking..."}/>
            }
        </PageContent>
    )
}
