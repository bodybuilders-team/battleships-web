import * as React from "react"
import {useEffect, useRef, useState} from "react"
import {EmbeddedSubEntity} from "../../../Services/media/siren/SubEntity"
import {handleError} from "../../../Services/utils/fetchSiren"
import PageContent from "../../Shared/PageContent"
import {GetGameOutputModel} from "../../../Services/services/games/models/games/getGame/GetGameOutput"
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService"
import LoadingSpinner from "../../Shared/LoadingSpinner"
import GameCard from "./GameCard"
import {useNavigate} from "react-router-dom"
import {Rels} from "../../../Utils/navigation/Rels"
import {useSession} from "../../../Utils/Session"
import Typography from "@mui/material/Typography"
import {abortableTo} from "../../../Utils/componentManagement/abortableUtils"
import {useNavigationState} from "../../../Utils/navigation/NavigationState";
import {useMountedSignal} from "../../../Utils/componentManagement/useMounted";

/**
 * Lobby component.
 */
export default function Lobby() {
    const navigate = useNavigate()

    const battleshipsService = useBattleshipsService()
    const session = useSession()

    const [error, setError] = useState<string | null>(null)
    const [games, setGames] = useState<EmbeddedSubEntity<GetGameOutputModel>[] | null>(null)
    const [gamesLoaded, setGamesLoaded] = useState(false)
    const navigatingToGameRef = useRef(false)
    const navigationState = useNavigationState()
    const mountedSignal = useMountedSignal()

    useEffect(() => {
        fetchGames()

        return () => {
            if (!navigatingToGameRef.current)
                navigationState.clearGameLinks()
        }
    }, [])

    /**
     * Fetches the games.
     */
    async function fetchGames() {
        if (gamesLoaded)
            return

        const [err, res] = await abortableTo(battleshipsService.gamesService.getGames({
            excludeUsername: session!.username,
            phases: ["WAITING_FOR_PLAYERS"]
        },mountedSignal))

        if (err) {
            handleError(err, setError)
            return
        }

        const filteredGames = res
            .getEmbeddedSubEntities<GetGameOutputModel>()

        setGames(filteredGames)
        setGamesLoaded(true)
    }

    /**
     * Handles the join game button click.
     *
     * @param joinGameLink the link to join the game
     */
    async function handleJoinGame(joinGameLink: string) {
        const [err, res] = await abortableTo(battleshipsService.gamesService.joinGame(joinGameLink,mountedSignal))

        if (err) {
            handleError(err, setError)
            return
        }

        navigatingToGameRef.current = true
        navigate(`/game/${res.properties!.gameId}`)
    }

    return (
        <PageContent title="Lobby" error={error}>
            {
                gamesLoaded
                    ? games?.length === 0
                        ? <Typography variant="h5" component="div" sx={{flexGrow: 1}}>No games available</Typography>
                        : games?.map(game =>
                            <GameCard key={game.properties?.id} game={game.properties!} onJoinGameRequest={() => {
                                handleJoinGame(game.getAction(Rels.JOIN_GAME))
                            }}/>
                        )
                    : <LoadingSpinner text={"Loading games..."}/>
            }
        </PageContent>
    )
}
