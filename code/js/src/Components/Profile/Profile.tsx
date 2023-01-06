import * as React from "react"
import {useSession} from "../../Utils/Session"
import {Card, CardContent} from "@mui/material"
import Box from "@mui/material/Box"
import Avatar from "@mui/material/Avatar"
import Typography from "@mui/material/Typography"
import Container from "@mui/material/Container"
import Grid from "@mui/material/Grid"
import GameHistory from "./GameHistory"
import OngoingGames from "./OngoingGames";

/**
 * Profile component.
 */
export default function Profile() {
    const session = useSession()

    return (
        <Box component="main">
            <Container maxWidth="lg">
                <Typography sx={{mb: 3}} variant="h4">Account</Typography>
                <Grid container spacing={3}>
                    <Grid item lg={4} md={6} xs={12}>
                        <Card>
                            <CardContent>
                                <Box sx={{
                                    alignItems: 'center',
                                    display: 'flex',
                                    flexDirection: 'column'
                                }}>
                                    <Avatar sx={{height: 64, mb: 2, width: 64}}/>
                                    <Typography color="textPrimary" gutterBottom variant="h5">
                                        {session!.username}
                                    </Typography>
                                </Box>
                            </CardContent>
                        </Card>
                    </Grid>
                    <Grid item lg={8} md={6} xs={12}>
                        <OngoingGames/>
                        <GameHistory/>
                    </Grid>
                </Grid>
            </Container>
        </Box>
    )
}
