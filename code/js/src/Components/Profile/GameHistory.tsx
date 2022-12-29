import * as React from "react"
import {useEffect, useState} from "react"
import {
    Card,
    CardContent,
    CardHeader,
    Divider,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow
} from '@mui/material'
import {useBattleshipsService} from "../../Services/NavigationBattleshipsService"
import {useSession} from "../../Utils/Session"
import {GetGameOutputModel} from "../../Services/services/games/models/games/getGame/GetGameOutput"
import {handleError} from "../../Services/utils/fetchSiren"
import {Game} from "../../Domain/games/game/Game"
import ErrorAlert from "../Shared/ErrorAlert"
import {useMountedSignal} from "../../Utils/componentManagement/useMounted"
import {abortableTo} from "../../Utils/componentManagement/abortableUtils"
import {useNavigationState} from "../../Utils/navigation/NavigationState";

/**
 * GameHistory component.
 */
export default function GameHistory() {
    const battleshipsService = useBattleshipsService()
    const session = useSession()

    const [error, setError] = useState<string | null>(null)
    const [games, setGames] = useState<Game[] | null>(null)
    const [gamesLoaded, setGamesLoaded] = useState(false)
    const navigationState = useNavigationState()

    useEffect(() => {
        fetchGames()

        return () => {
            navigationState.clearGameLinks()
        }
    }, [])

    const mountedSignal = useMountedSignal()

    /**
     * Fetches the games.
     */
    async function fetchGames() {
        if (gamesLoaded)
            return

        const [err, res] = await abortableTo(battleshipsService.gamesService.getGames({
            username: session!.username,
            phases: ["FINISHED"]
        }, mountedSignal))

        if (err) {
            handleError(err, setError)
            return
        }

        const games = res
            .getEmbeddedSubEntities<GetGameOutputModel>()

        setGames(games.map(game => new Game(game.properties!)))
        setGamesLoaded(true)
    }

    return (
        <Card>
            <CardHeader
                subheader="Last 10 games"
                title="Game History"
            />
            <ErrorAlert error={error}/>
            <Divider/>
            <CardContent>
                <TableContainer component={Paper} sx={{width: '100%'}}>
                    <Table stickyHeader aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell>Game</TableCell>
                                <TableCell>Opponent</TableCell>
                                <TableCell>Winner</TableCell>
                                <TableCell>End Cause</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {
                                games?.map(game => (
                                    <TableRow key={game.id} sx={{'&:last-child td, &:last-child th': {border: 0}}}>
                                        <TableCell component="th" scope="row">{game.name}</TableCell>
                                        <TableCell>{game.getOpponent(session?.username!)?.username}</TableCell>
                                        <TableCell>{game.state.winner}</TableCell>
                                        <TableCell>{game.state.endCause}</TableCell>
                                    </TableRow>
                                ))
                            }
                        </TableBody>
                    </Table>
                </TableContainer>
            </CardContent>
        </Card>
    )
}
