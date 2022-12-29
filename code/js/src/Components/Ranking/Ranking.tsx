import * as React from "react"
import {useEffect, useState} from "react"
import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material"
import {User} from "../../Domain/users/User"
import {EmbeddedSubEntity} from "../../Services/media/siren/SubEntity"
import {handleError} from "../../Services/utils/fetchSiren"
import PageContent from "../Shared/PageContent"
import {useBattleshipsService} from "../../Services/NavigationBattleshipsService"
import {abortableTo} from "../../Utils/componentManagement/abortableUtils"
import {useMountedSignal} from "../../Utils/componentManagement/useMounted";

/**
 * Ranking component.
 */
export default function Ranking() {
    const battleshipsService = useBattleshipsService()
    const [error, setError] = useState<string | null>(null)
    const [ranking, setRanking] = useState<User[]>([])
    const mountedSignal = useMountedSignal()

    useEffect(() => {
        fetchRanking()
    }, [])

    /**
     * Fetches the ranking.
     */
    async function fetchRanking() {
        const [err, res] = await abortableTo(battleshipsService.usersService.getUsers(mountedSignal))

        if (err) {
            handleError(err, setError)
            return
        }

        if (res?.entities === undefined)
            throw new Error("Entities are undefined")

        const users = res.entities.map(entity => (entity as EmbeddedSubEntity<User>).properties as User)

        setRanking(users)
    }

    return (
        <PageContent title="Ranking" error={error}>
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
                        {
                            ranking.map((user, index) => (
                                <TableRow key={user.username} sx={{'&:last-child td, &:last-child th': {border: 0}}}>
                                    <TableCell component="th" scope="row">{index + 1}</TableCell>
                                    <TableCell>{user.username}</TableCell>
                                    <TableCell>{user.points}</TableCell>
                                </TableRow>
                            ))
                        }
                    </TableBody>
                </Table>
            </TableContainer>
        </PageContent>
    )
}
