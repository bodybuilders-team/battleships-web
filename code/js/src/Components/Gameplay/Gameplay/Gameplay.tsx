import * as React from "react"
import {useEffect, useState} from "react"
import {handleError} from "../../../Services/utils/fetchSiren"
import {GetGameOutputModel} from "../../../Services/services/games/models/games/getGame/GetGameOutput"
import LoadingSpinner from "../../Shared/LoadingSpinner"
import PageContent from "../../Shared/PageContent"
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService"
import ShootingGameplay from "./Shooting/ShootingGameplay"
import BoardSetupGameplay from "./BoardSetup/BoardSetupGameplay"
import {Game} from "../../../Domain/games/game/Game"
import GameFinished from "../Shared/EndGame/GameFinished"
import {abortableTo, setAbortableTimeout} from "../../../Utils/componentManagement/abortableUtils"
import {useNavigationState} from "../../../Utils/navigation/NavigationState"
import {useMountedSignal} from "../../../Utils/componentManagement/useMounted"
import {useNavigate, useParams} from "react-router-dom"
import Typography from "@mui/material/Typography"
import Button from "@mui/material/Button"
import {Rels} from "../../../Utils/navigation/Rels"

/**
 * Gameplay component.
 */
export default function Gameplay() {
    const {id} = useParams()

    const battleshipsService = useBattleshipsService()
    const navigate = useNavigate()

    const [game, setGame] = useState<Game | null>(null)
    const [error, setError] = useState<string | null>(null)
    const navigationState = useNavigationState()
    const mountedSignal = useMountedSignal()

    useEffect(() => {
        fetchGame()

        return () => {
            navigationState.clearGameLinks()
        }
    }, [])

    async function fetchGame() {
        if (!navigationState.links.get(Rels.GAME))
            await fetchGameLink()

        await fetchGameWithLink()
    }

    async function fetchGameLink() {
        if (id === undefined) {
            setError("Game ID is undefined")
            throw new Error("Game ID is undefined")
        }

        const gameId = parseInt(id)

        const [err, res] = await abortableTo(battleshipsService.gamesService.getGames({
            ids: [gameId]
        }, mountedSignal))

        if (err) {
            handleError(err, setError, navigate)
            throw new Error("Failed to fetch game link")
        }

        const games = res
            .getEmbeddedSubEntities<GetGameOutputModel>()

        if (games.length <= 0) {
            setError("Game not found")
            throw new Error("Game not found")
        }

        const game = new Game(games[0].properties!)

        navigationState.links.set(Rels.GAME, navigationState.links.get(`${Rels.GAME}-${game.id}`)!)
        navigationState.links.set(Rels.GAME_STATE, navigationState.links.get(`${Rels.GAME_STATE}-${game.id}`)!)
    }

    /**
     * Fetches the game.
     */
    async function fetchGameWithLink() {
        const [err, res] = await abortableTo(
            battleshipsService.gamesService.getGame(mountedSignal)
        )

        if (err) {
            handleError(err, setError, navigate)
            throw new Error("Failed to fetch game")
        }

        if (res?.properties === undefined)
            throw new Error("Properties are undefined")

        const newGame = new Game(res.properties as GetGameOutputModel)
        setGame(newGame)

        if (newGame.state.phase === "WAITING_FOR_PLAYERS")
            setAbortableTimeout(() => fetchGameLink(), 1000, mountedSignal)
    }


    if (game?.state.phase === "DEPLOYING_FLEETS")
        return (
            <BoardSetupGameplay
                finalTime={game.state.phaseEndTime}
                gameConfig={game!.config}
                onFinished={fetchGameWithLink}
            />
        )
    else if (game?.state.phase === "IN_PROGRESS")
        return <ShootingGameplay game={game!}/>
    else if (game?.state.phase === "FINISHED")
        return <GameFinished game={game}/>
    else if (game?.state.phase === "WAITING_FOR_PLAYERS")
        return <PageContent>
            <Typography variant="h2">The game is still waiting for players</Typography>
            <Button onClick={() => {
                navigate("/gameplay-menu")
            }}>
            </Button>
        </PageContent>
    else
        return (
            <PageContent error={error}>
                <LoadingSpinner text={"Loading game..."}/>
            </PageContent>
        )
}
