import * as React from "react";
import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import {useSession} from "../../Utils/Session";
import {Card, CardActions, CardContent, Divider} from "@mui/material";
import Box from "@mui/material/Box";
import Avatar from "@mui/material/Avatar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import ProfileDetails from "./ProfileDetails";
import {InsertPhotoRounded} from "@mui/icons-material";
import {throwError} from "../../Services/utils/errorUtils";

// TODO: To change later
const user = {
    avatar: '',
    city: 'Lisbon',
    country: 'Portugal'
};

/**
 * Profile component.
 */
function Profile() {
    const session = useSession() ?? throwError("Session is null");

    return (
        <Box component="main">
            <Container maxWidth="lg">
                <Typography sx={{mb: 3}} variant="h4">Account</Typography>
                <Grid container spacing={3}>
                    <Grid item lg={4} md={6} xs={12}>
                        <Card>
                            <CardContent>
                                <Box
                                    sx={{
                                        alignItems: 'center',
                                        display: 'flex',
                                        flexDirection: 'column'
                                    }}
                                >
                                    <Avatar
                                        src={user.avatar}
                                        sx={{
                                            height: 64,
                                            mb: 2,
                                            width: 64
                                        }}
                                    />
                                    <Typography
                                        color="textPrimary"
                                        gutterBottom
                                        variant="h5"
                                    >
                                        {session.username}
                                    </Typography>
                                    <Typography color="textSecondary" variant="body2">
                                        {`${user.city} ${user.country}`}
                                    </Typography>
                                </Box>
                            </CardContent>
                            <Divider/>
                            <CardActions>
                                <Button
                                    color="primary"
                                    fullWidth
                                    variant="text"
                                    startIcon={<InsertPhotoRounded/>}
                                >
                                    Upload Picture
                                </Button>
                            </CardActions>
                        </Card>
                    </Grid>
                    <Grid item lg={8} md={6} xs={12}>
                        <ProfileDetails/>
                    </Grid>
                </Grid>
            </Container>
        </Box>
    );
}

export default Profile;
