import * as React from "react";
import Box from "@mui/material/Box";
import PlayArrowIcon from "@mui/icons-material/PlayArrow";
import {useNavigate} from "react-router-dom";
import Typography from "@mui/material/Typography";
import {useLoggedIn, useSession} from "../../Utils/Session";
import {Login} from "@mui/icons-material";
import Logo from "../../Assets/logo.png";
import PageContent from "../Utils/PageContent";
import {MenuButton} from "../Utils/MenuButton";
import Footer from "../../Layouts/Footer";

/**
 * Home page component.
 */
function Home() {
    const navigate = useNavigate();
    const loggedIn = useLoggedIn();
    const session = useSession();
    const [error, setError] = React.useState<string | null>(null);


    return (
        <>
            <PageContent title={"Battleships"} error={error}>
                <Typography variant="h5" component="h2" gutterBottom>
                    Welcome to the Battleships Game{loggedIn ? ", " + session!.username : ""}!
                </Typography>
                <Typography variant="h6" gutterBottom>
                    This is a simple game of battleships where you can play against other players online.
                </Typography>
                <img src={Logo} alt="logo" width="300" height="300"/>
                <Box sx={{mt: 1}}>
                    <Typography variant="h6" gutterBottom>
                        {
                            loggedIn
                                ? "Simply click the button below to start playing!"
                                : "You need to be logged in to play. Please log in or sign up to play."
                        }
                    </Typography>
                    <MenuButton
                        title={loggedIn ? "Play" : "Log in"}
                        icon={loggedIn ? <PlayArrowIcon/> : <Login/>}
                        onClick={() => navigate(loggedIn ? '/gameplay-menu' : '/login')}
                    />
                </Box>
            </PageContent>
            <Footer/>
        </>
    );
}

export default Home;
