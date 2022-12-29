import * as React from "react"
import {useState} from "react"
import {Game} from "../../../../Domain/games/game/Game"
import {useInterval} from "../TimersHooks/useInterval"
import {Rels} from "../../../../Utils/navigation/Rels"
import {GetGameOutputModel} from "../../../../Services/services/games/models/games/getGame/GetGameOutput"
import {useBattleshipsService} from "../../../../Services/NavigationBattleshipsService"
import {useNavigate} from "react-router-dom"
import GameFinished from "./GameFinished"
import {Uris} from "../../../../Utils/navigation/Uris"
import {abortableTo} from "../../../../Utils/abortableUtils"
import HOME = Uris.HOME;
import {useMountedSignal} from "../../../../Utils/useMounted";

/**
 * Properties for the FetchedEndGamePopup component.
 *
 * @property open whether the popup is open
 * @property onError callback for when an error occurs fetching
 */
interface FetchedEndGamePopupProps {
    open: boolean
    onError: (err: Error) => void
}

const POLLING_DELAY = 1000

/**
 * EndGamePopup that fetches the game.
 */
export default function FetchedEndGamePopup({open, onError}: FetchedEndGamePopupProps) {
    const [game, setGame] = useState<Game | null>(null)
    const battleshipsService = useBattleshipsService()
    const navigate = useNavigate()

    useInterval(fetchGame, POLLING_DELAY, [open])

    const mountedSignal = useMountedSignal()
    /**
     * Fetches the game.
     */
    async function fetchGame() {
        if (!open) return false

        if (!battleshipsService.links.get(Rels.GAME)) {
            console.log("No game link found in fetched end game popup")
            navigate(HOME)
            return true
        }

        const [err, res] = await abortableTo(battleshipsService.gamesService.getGame(mountedSignal))

        if (err) {
            onError(err)
            return true
        }

        if (res?.properties === undefined)
            throw new Error("Properties are undefined")

        if (res.properties.state.phase === "FINISHED") {
            const newGame = new Game(res.properties as GetGameOutputModel)
            setGame(newGame)
            return true
        }

        return false
    }

    if (game != null)
        return <GameFinished game={game}/>
    else
        return null
}
