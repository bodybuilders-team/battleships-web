import * as React from 'react';
import './App.css';
import NavBar from "./components/NavBar";
import {Route, Routes} from 'react-router-dom';
import Home from "./components/Home";
import Login from "./components/Login";
import Ranking from "./components/Ranking";
import Profile from "./components/Profile";
import Gameplay from "./components/Gameplay";
import Register from './components/Register';
import {Auth} from "./utils/Session";

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

                <footer>
                    <p>
                        Made with ❤️ by group 3 of DAW@ISEL in 2022/2023<br/>
                        48089 André Páscoa<br/>
                        48280 André Jesus<br/>
                        48287 Nyckollas Brandão
                    </p>
                </footer>
            </div>
        </Auth>
    );
}

export default App;
