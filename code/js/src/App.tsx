import * as React from 'react'
import './App.css'
import NavBar from "./Layouts/NavBar"
import {Navigate, Route, Routes} from 'react-router-dom'
import {useBattleshipsService} from "./Services/NavigationBattleshipsService"
import {useLoggedIn} from "./Utils/Session"
import Home from './Components/Home/Home'
import Login from "./Components/Authentication/Login/Login"
import Register from "./Components/Authentication/Register/Register"
import Profile from "./Components/Profile/Profile"
import Ranking from "./Components/Ranking/Ranking"
import GameplayMenu from "./Components/Gameplay/GameplayMenu/GameplayMenu"
import Gameplay from "./Components/Gameplay/Gameplay/Gameplay"
import Matchmake from "./Components/Gameplay/Matchmake/Matchmake"
import CreateGame from "./Components/Gameplay/CreateGame/CreateGame"
import Lobby from "./Components/Gameplay/Lobby/Lobby"
import About from "./Components/About/About"
import {Uris} from "./Utils/navigation/Uris"
import {useMountedSignal} from "./Utils/useMounted"
import {useAbortableEffect} from "./Utils/abortableUtils"
import HOME = Uris.HOME;
import LOGIN = Uris.LOGIN;
import REGISTER = Uris.REGISTER;
import PROFILE = Uris.PROFILE;
import RANKING = Uris.RANKING;
import GAMEPLAY_MENU = Uris.GAMEPLAY_MENU;
import GAME = Uris.GAME;
import MATCHMAKE = Uris.MATCHMAKE;
import CREATE_GAME = Uris.CREATE_GAME;
import LOBBY = Uris.LOBBY;
import ABOUT = Uris.ABOUT;

/**
 * App component.
 */
export default function App() {
    const loggedIn = useLoggedIn()
    const battleshipsService = useBattleshipsService()
    const mountedSignal = useMountedSignal()

    useAbortableEffect(() => {
        getHome()
    })

    /**
     * Fetches the home page.
     */
    async function getHome() {
        await battleshipsService.getHome(mountedSignal)
    }

    /**
     * Protection route component, redirects to login page if not logged in.
     *
     * @param children the children to render
     */
    function ProtectedRoute({children}: { children: React.ReactElement }) {
        if (!loggedIn)
            return <Navigate to={LOGIN} replace/>

        return children
    }

    /**
     * Public route component, redirects to profile page if logged in.
     * Used for login and register pages.
     *
     * @param children the children to render
     */
    function PublicRoute({children}: { children: React.ReactElement }) {
        if (loggedIn)
            return <Navigate to={PROFILE} replace/>

        return children
    }

    return (
        <div className="App">
            <NavBar/>

            <div className="App-content">
                <Routes>
                    <Route path={HOME} element={<Home/>}/>

                    <Route path={LOGIN} element={<PublicRoute><Login/></PublicRoute>}/>
                    <Route path={REGISTER} element={<PublicRoute><Register/></PublicRoute>}/>
                    <Route path={PROFILE} element={<ProtectedRoute><Profile/></ProtectedRoute>}/>

                    <Route path={RANKING} element={<Ranking/>}/>

                    <Route path={GAMEPLAY_MENU} element={<ProtectedRoute><GameplayMenu/></ProtectedRoute>}/>
                    <Route path={GAME} element={<ProtectedRoute><Gameplay/></ProtectedRoute>}/>
                    <Route path={MATCHMAKE} element={<ProtectedRoute><Matchmake/></ProtectedRoute>}/>
                    <Route path={CREATE_GAME} element={<ProtectedRoute><CreateGame/></ProtectedRoute>}/>
                    <Route path={LOBBY} element={<ProtectedRoute><Lobby/></ProtectedRoute>}/>

                    <Route path={ABOUT} element={<About/>}/>
                </Routes>
            </div>
            {/*<BoardSetup boardSize={10} ships={*/}
            {/*    defaultShipTypes*/}
            {/*} onBoardReady={(board) => {*/}
            {/*    console.log(board)*/}
            {/*}} finalTime={new Date().getTime() + 100000}*/}
            {/*            error={null}*/}
            {/*            onLeaveGame={() => {*/}
            {/*            }}*/}
            {/*            onTimeUp={() => {*/}
            {/*            }}/>*/}

        </div>
    )
}
