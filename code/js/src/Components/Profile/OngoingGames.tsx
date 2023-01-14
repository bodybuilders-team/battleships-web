import {useBattleshipsService} from "../../Services/NavigationBattleshipsService"
import {useSession} from "../../Utils/Session"
import * as React from "react"
import {useEffect, useState} from "react"
import {Game} from "../../Domain/games/game/Game"
import {useNavigationState} from "../../Utils/navigation/NavigationState"
import {useMountedSignal} from "../../Utils/componentManagement/useMounted"
import {abortableTo} from "../../Utils/componentManagement/abortableUtils"
import {handleError} from "../../Services/utils/fetchSiren"
import {GetGameOutputModel} from "../../Services/services/games/models/games/getGame/GetGameOutput"
import PlayArrowIcon from '@mui/icons-material/PlayArrow'
import {
    Button,
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
} from "@mui/material"
import ErrorAlert from "../Shared/ErrorAlert"
import {Rels} from "../../Utils/navigation/Rels"
import {useNavigate} from "react-router-dom"

/**
 * Ongoing games component.
 *
 */
export default function OngoingGames() {
    const battleshipsService = useBattleshipsService()
    const session = useSession()

    const [error, setError] = useState<string | null>(null)
    const [games, setGames] = useState<Game[] | null>(null)
    const navigationState = useNavigationState()
    const navigate = useNavigate()

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
        const [err, res] = await abortableTo(battleshipsService.gamesService.getGames({
            username: session!.username,
            phases: ["DEPLOYING_FLEETS", "IN_PROGRESS"]
        }, mountedSignal))

        if (err) {
            handleError(err, setError, navigate)
            return
        }

        const games = res
            .getEmbeddedSubEntities<GetGameOutputModel>()

        setGames(games.map(game => new Game(game.properties!)))
    }

    return (
        <Card>
            <CardHeader
                subheader="Last 10 games"
                title="Ongoing games"
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
                                <TableCell>Play</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {
                                games?.map(game => (
                                    <TableRow key={game.id} sx={{'&:last-child td, &:last-child th': {border: 0}}}>
                                        <TableCell component="th" scope="row">{game.name}</TableCell>
                                        <TableCell>{game.getOpponent(session?.username!)?.username}</TableCell>
                                        <TableCell>
                                            <Button onClick={
                                                () => {
                                                    navigationState.links.set(Rels.GAME,
                                                        navigationState.links.get(`${Rels.GAME}-${game.id}`)!
                                                    )

                                                    navigate(`/game/${game.id}`)
                                                }
                                            }>
                                                <PlayArrowIcon/>
                                            </Button>
                                        </TableCell>
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
