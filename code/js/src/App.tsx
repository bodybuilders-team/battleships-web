import * as React from 'react';
import './App.css';
import NavBar from "./Layouts/NavBar";
import {Route, Routes} from 'react-router-dom';
import Home from "./Components/Home/Home";
import Login from "./Components/Authentication/Login";
import Ranking from "./Components/Ranking/Ranking";
import Profile from "./Components/Profile/Profile";
import Gameplay from "./Components/Gameplay/Gameplay";
import Register from './Components/Authentication/Register';
import {Auth} from "./Utils/Session";
import Footer from "./Layouts/Footer";
import About from "./Components/About/About";
import GameplayMenu from "./Components/Gameplay/GameplayMenu/GameplayMenu";

/**
 * App component.
 */
function App() {
    return (
        <Auth>
            <div className="App">
                <NavBar/>

                <div className="App-content">
                    <Routes>
                        <Route path="/" element={<Home/>}/>
                        <Route path="/login" element={<Login/>}/>
                        <Route path="/register" element={<Register/>}/>
                        <Route path="/ranking" element={<Ranking/>}/>
                        <Route path="/profile" element={<Profile/>}/> {/*TODO: Make inaccesible if not logged in*/}
                        <Route path="/gameplay-menu" element={<GameplayMenu/>}/> {/*TODO: Same*/}
                        <Route path="/gameplay" element={<Gameplay/>}/> {/*TODO: Same*/}
                        <Route path="/about" element={<About/>}/>
                    </Routes>
                </div>

                <Footer/>
            </div>
        </Auth>
    );
}

export default App;
