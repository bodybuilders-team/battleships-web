import * as React from 'react';
import {useEffect} from 'react';
import './App.css';
import NavBar from "./Layouts/NavBar";
import {Navigate, Route, Routes} from 'react-router-dom';
import {useNavigationState} from "./Utils/navigation/NavigationState";
import {useBattleshipsService} from "./Services/NavigationBattleshipsService";
import {useLoggedIn} from "./Utils/Session";
import Home from './Components/Home/Home';
import Login from "./Components/Authentication/Login/Login";
import Register from "./Components/Authentication/Register/Register";
import Ranking from "./Components/Ranking/Ranking";
import Profile from "./Components/Profile/Profile";
import GameplayMenu from "./Components/Gameplay/GameplayMenu/GameplayMenu";
import Gameplay from "./Components/Gameplay/Gameplay/Gameplay";
import About from "./Components/About/About";
import Matchmake from "./Components/Gameplay/Matchmake/Matchmake";
import Lobby from "./Components/Gameplay/Lobby/Lobby";
import CreateGame from "./Components/Gameplay/CreateGame/CreateGame";

/**
 * App component.
 */
export default function App() {
    const navigationState = useNavigationState();
    const loggedIn = useLoggedIn();
    const battleshipsService = useBattleshipsService();

    useEffect(() => {
        async function getHome() {
            await battleshipsService.getHome();
            navigationState.setLinks(battleshipsService.links);
        }

        getHome();
    }, [])


    /**
     * Protection route component, redirects to login page if not logged in.
     *
     * @param children the children to render
     */
    function ProtectedRoute({children}: { children: React.ReactElement }) {
        if (!loggedIn)
            return <Navigate to="/login" replace/>;

        return children;
    }

    /**
     * Public route component, redirects to profile page if logged in.
     * Used for login and register pages.
     *
     * @param children the children to render
     */
    function PublicRoute({children}: { children: React.ReactElement }) {
        if (loggedIn)
            return <Navigate to="/profile" replace/>;

        return children;
    }

    return (
        <div className="App">
            <NavBar/>

            <div className="App-content">
                <Routes>
                    <Route path="/" element={<Home/>}/>

                    <Route path="/login" element={<PublicRoute><Login/></PublicRoute>}/>
                    <Route path="/register" element={<PublicRoute><Register/></PublicRoute>}/>
                    <Route path="/profile" element={<ProtectedRoute><Profile/></ProtectedRoute>}/>

                    <Route path="/ranking" element={<Ranking/>}/>

                    <Route path="/gameplay-menu" element={<ProtectedRoute><GameplayMenu/></ProtectedRoute>}/>
                    <Route path="/game/:id" element={<ProtectedRoute><Gameplay/></ProtectedRoute>}/>
                    <Route path="/matchmake" element={<ProtectedRoute><Matchmake/></ProtectedRoute>}/>
                    <Route path="/create-game" element={<ProtectedRoute><CreateGame/></ProtectedRoute>}/>
                    <Route path="/lobby" element={<ProtectedRoute><Lobby/></ProtectedRoute>}/>

                    <Route path="/about" element={<About/>}/>
                </Routes>
            </div>
        </div>
    );
}
