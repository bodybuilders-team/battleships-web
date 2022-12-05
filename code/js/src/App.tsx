import * as React from 'react';
import {useEffect} from 'react';
import './App.css';
import NavBar from "./Layouts/NavBar";
import {Navigate, Route, Routes} from 'react-router-dom';
import Home from "./Components/Home/Home";
import Login from "./Components/Authentication/Login";
import Ranking from "./Components/Ranking/Ranking";
import Gameplay from "./Components/Gameplay/Gameplay/Gameplay";
import Register from './Components/Authentication/Register';
import Footer from "./Layouts/Footer";
import About from "./Components/About/About";
import GameplayMenu from "./Components/Gameplay/GameplayMenu/GameplayMenu";
import {useNavigationState} from "./Utils/NavigationStateProvider";
import {useBattleshipsService} from "./Services/NavigationBattleshipsService";
import {useLoggedIn} from "./Utils/Session";
import Profile from "./Components/Profile/Profile";

/**
 * App component.
 */
function App() {
    const navigationState = useNavigationState()

    const loggedIn = useLoggedIn()
    const [battleshipsService, setBattleshipsService] = useBattleshipsService()

    useEffect(() => {
        async function getHome() {
            await battleshipsService.getHome()

            navigationState.setLinks(battleshipsService.links);
        }

        getHome()
    }, [])


    /**
     * Protection route component, redirects to home page if not logged in.
     * @param children the children to render
     */
    function ProtectedRoute({children}: { children: React.ReactElement }) {
        if (!loggedIn)
            return <Navigate to="/" replace/>;

        return children;
    }

    return (
        <div className="App">
            <NavBar/>

            <div className="App-content">
                <Routes>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/login" element={<Login/>}/>
                    <Route path="/register" element={<Register/>}/>
                    <Route path="/ranking" element={<Ranking/>}/>
                    <Route path="/profile" element={
                        <ProtectedRoute>
                            <Profile/>
                        </ProtectedRoute>

                    }/>
                    <Route path="/gameplay-menu" element={
                        <ProtectedRoute>
                            <GameplayMenu/>
                        </ProtectedRoute>
                    }/>
                    <Route path="/gameplay" element={
                        <ProtectedRoute>
                            <Gameplay/>
                        </ProtectedRoute>
                    }/>
                    <Route path="/about" element={
                        <ProtectedRoute>
                            <About/>
                        </ProtectedRoute>
                    }/>
                </Routes>
            </div>

            <Footer/>
        </div>
    );
}


export default App;
