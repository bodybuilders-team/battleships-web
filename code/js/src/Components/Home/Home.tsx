import * as React from "react";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import PlayArrowIcon from "@mui/icons-material/PlayArrow";
import {useNavigate} from "react-router-dom";
import Typography from "@mui/material/Typography";
import {useSession} from "../../Utils/Session";
import {Login} from "@mui/icons-material";
import Logo from "../../Assets/logo.png";

/**
 * Home component.
 */
function Home() {
    const navigate = useNavigate();
    const session = useSession();

    return (
        <div>
            <h1>Battleships</h1>
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Typography variant="h5" component="h2" gutterBottom>
                    Welcome to the Battleships Game{session ? ", " + session.username : ""}!
                </Typography>
                <Typography variant="h6" gutterBottom>
                    This is a simple game of battleships where you can play against other players online.
                </Typography>
                <img src={Logo} alt="logo" width="300" height="300"/>
                <Box sx={{mt: 1}}>
                    <Typography variant="h6" gutterBottom>
                        {
                            session
                                ? "Simply click the button below to start playing!"
                                : "You need to be logged in to play. Please log in or sign up to play."
                        }
                    </Typography>

                    <Button
                        fullWidth
                        size="large"
                        variant="contained"
                        sx={{mt: 3, mb: 2}}
                        startIcon={session ? <PlayArrowIcon/> : <Login/>}
                        color="primary"
                        onClick={() => {
                            navigate(session ? '/gameplay' : '/login');
                        }}
                    >
                        {session ? "Play" : "Log in"}
                    </Button>
                </Box>
            </Box>
        </div>
    );
}

export default Home;
