import * as React from "react"
import {useState} from "react"
import Box from "@mui/material/Box"
import {useNavigate} from "react-router-dom"
import Typography from "@mui/material/Typography"
import {useLoggedIn, useSession} from "../../Utils/Session"
import {LoginRounded, PlayArrowRounded} from "@mui/icons-material"
import Logo from "../../Assets/logo.png"
import PageContent from "../Shared/PageContent"
import MenuButton from "../Shared/MenuButton"
import Footer from "../../Layouts/Footer"

/**
 * Home page component.
 */
export default function Home() {
    const navigate = useNavigate()

    const session = useSession()
    const loggedIn = useLoggedIn()

    const [error] = useState<string | null>(null)

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
                        icon={loggedIn ? <PlayArrowRounded/> : <LoginRounded/>}
                        onClick={() => navigate(loggedIn ? '/gameplay-menu' : '/login')}
                    />
                </Box>
            </PageContent>
            <Footer/>
        </>
    )
}
