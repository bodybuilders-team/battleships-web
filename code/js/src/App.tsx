import * as React from 'react';
import './App.css';
import NavBar from "./Components/Utils/NavBar";
import {Route, Routes} from 'react-router-dom';
import Home from "./Components/Home/Home";
import Login from "./Components/Authentication/Login";
import Ranking from "./Components/Ranking/Ranking";
import Profile from "./Components/Profile/Profile";
import Gameplay from "./Components/Gameplay/Gameplay";
import Register from './Components/Authentication/Register';
import {Auth} from "./utils/Session";
import Footer from "./Components/Utils/Footer";

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
                        <Route path="/profile" element={<Profile/>}/>
                        <Route path="/gameplay" element={<Gameplay/>}/>
                    </Routes>
                </div>

                <Footer/>
            </div>
        </Auth>
    );
}

export default App;
