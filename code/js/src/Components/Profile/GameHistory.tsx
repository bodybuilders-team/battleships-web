import * as React from "react";
import {useEffect, useState} from "react";
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
} from '@mui/material';
import {useBattleshipsService} from "../../Services/NavigationBattleshipsService";
import {useSession} from "../../Utils/Session";
import {GetGameOutputModel} from "../../Services/services/games/models/games/getGame/GetGameOutput";
import to from "../../Utils/await-to";
import {handleError} from "../../Services/utils/fetchSiren";
import {Game} from "../../Domain/games/game/Game";
import ErrorAlert from "../Shared/ErrorAlert";

/**
 * GameHistory component.
 */
export default function GameHistory() {
    const battleshipsService = useBattleshipsService();
    const session = useSession();

    const [error, setError] = useState<string | null>(null);
    const [games, setGames] = useState<Game[] | null>(null);
    const [gamesLoaded, setGamesLoaded] = useState(false);

    useEffect(() => {
        fetchGames()
    }, []);

    /**
     * Fetches the games.
     */
    async function fetchGames() {
        if (gamesLoaded)
            return;

        const [err, res] = await to(battleshipsService.gamesService.getGames());

        if (err) {
            handleError(err, setError);
            return;
        }

        const filteredGames = res
            .getEmbeddedSubEntities<GetGameOutputModel>()
            .filter(game =>
                game.properties?.state.phase === "FINISHED" &&
                game.properties?.creator !== session?.username // TODO: games from user
            );

        setGames(filteredGames.map(game => new Game(game.properties!)));
        setGamesLoaded(true);
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
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {
                                games?.map(game => (
                                    <TableRow key={game.name} sx={{'&:last-child td, &:last-child th': {border: 0}}}>
                                        <TableCell component="th" scope="row">{game.name}</TableCell>
                                        <TableCell>{game.getOpponent(session?.username!)?.username}</TableCell>
                                        <TableCell>{game.state.winner}</TableCell>
                                    </TableRow>
                                ))
                            }
                        </TableBody>
                    </Table>
                </TableContainer>
            </CardContent>
        </Card>
    );
}
