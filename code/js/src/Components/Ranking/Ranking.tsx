import * as React from "react";
import {useEffect} from "react";
import {Alert, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";
import {User} from "../../Domain/users/User";
import {EmbeddedSubEntity} from "../../Services/utils/siren/SubEntity";
import to from "../../Utils/await-to";
import * as usersService from '../../Services/users/UsersService';
import Box from "@mui/material/Box";
import {handleError} from "../../Services/utils/fetchSiren";

/**
 * Ranking component.
 */
function Ranking() {

    const [ranking, setRanking] = React.useState<User[]>([]);
    const [error, setError] = React.useState<string | null>(null);

    useEffect(() => {
        if (!ranking || ranking.length === 0) {
            const fetchRanking = async () => {
                const [err, res] = await to(usersService.getUsers("http://localhost:8080/users"));

                if (err) {
                    handleError(err, setError);
                    return;
                }

                if (res?.entities === undefined)
                    throw new Error("Entities are undefined");

                const users = res.entities.map(entity => (entity as EmbeddedSubEntity<User>).properties as User);
                setRanking(users);
            }

            fetchRanking();
        }
    });

    return (
        <div>
            <h1>Ranking</h1>
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                {error && <Alert severity="error">{error}</Alert>}
                <TableContainer component={Paper} sx={{width: '600px'}}>
                    <Table stickyHeader aria-label="simple table">
                        <TableHead>
                            <TableRow>
                                <TableCell>Rank</TableCell>
                                <TableCell>Username</TableCell>
                                <TableCell>Points</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {ranking.map((user, index) => (
                                <TableRow
                                    key={user.username}
                                    sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                >
                                    <TableCell component="th" scope="row">{index + 1}</TableCell>
                                    <TableCell>{user.username}</TableCell>
                                    <TableCell>{user.points}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            </Box>
        </div>
    );
}

export default Ranking;
