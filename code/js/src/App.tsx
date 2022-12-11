import * as React from 'react';
import {useEffect} from 'react';
import './App.css';
import NavBar from "./Layouts/NavBar";
import {Navigate, Route, Routes} from 'react-router-dom';
import {useNavigationState} from "./Utils/navigation/NavigationStateProvider";
import {useBattleshipsService} from "./Services/NavigationBattleshipsService";
import {useLoggedIn, useSessionManager} from "./Utils/Session";
import Gameplay, {Game, GameConfig} from "./Components/Gameplay/Gameplay/Gameplay";
import Home from "./Components/Home/Home";
import Login from "./Components/Authentication/Login/Login";
import Register from "./Components/Authentication/Register/Register";
import Ranking from "./Components/Ranking/Ranking";
import Profile from "./Components/Profile/Profile";
import GameplayMenu from "./Components/Gameplay/GameplayMenu/GameplayMenu";
import Shooting, {TileHitView} from "./Components/Gameplay/Gameplay/Shooting";
import BoardSetup from './Components/Gameplay/BoardSetup/BoardSetup';
import {defaultShipTypes, ShipType} from "./Domain/games/ship/ShipType";
import {Coordinate} from "./Domain/games/Coordinate";
import {Ship} from "./Domain/games/ship/Ship";
import {Orientation} from "./Domain/games/ship/Orientation";

/**
 * App component.
 */
function App() {
    const navigationState = useNavigationState()

    const loggedIn = useLoggedIn()
    const battleshipsService = useBattleshipsService()

    useEffect(() => {
        async function getHome() {
            await battleshipsService.getHome()

            navigationState.setLinks(battleshipsService.links);
        }

        getHome()
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

                    <Route path="/ranking" element={<Ranking/>}/>

                    <Route path="/profile" element={<ProtectedRoute><Profile/></ProtectedRoute>}/>
                    <Route path="/gameplay-menu" element={<ProtectedRoute><GameplayMenu/></ProtectedRoute>}/>
                    <Route path="/game/:id" element={<ProtectedRoute><Gameplay/></ProtectedRoute>}/>

                    <Route path="/about"/>
                </Routes>
            </div>

            {/*// TODO: Check how to do previews.*/}
            {/*<BoardSetup boardSize={10} ships={*/}
            {/*    defaultShipTypes*/}
            {/*} onBoardReady={(board)=>{*/}
            {/*    console.log(board)*/}
            {/*}} finalTime={new Date().getTime()+100000}/>*/}

            {/*{*/}
            {/*    (() => {*/}
            {/*        const session = useSessionManager()*/}
            {/*        session.setSession({*/}
            {/*            username: "test",*/}
            {/*            accessToken: "",*/}
            {/*            refreshToken: "",*/}
            {/*            userHomeLink: ""*/}
            {/*        })*/}
            {/*        return null*/}
            {/*    })()*/}
            {/*}*/}
            {/*<Shooting finalTime={new Date().getTime()+100000} game={new Game({*/}
            {/*    config: {*/}
            {/*        gridSize: 10,*/}
            {/*        maxTimeForLayoutPhase: 0,*/}
            {/*        maxTimePerRound: 0,*/}
            {/*        shipTypes: GameConfig.mapToShipTypesModel(defaultShipTypes),*/}
            {/*        shotsPerRound: 3*/}
            {/*    }, creator: "", id: "", name: "", players: [{*/}
            {/*        username: "test",*/}
            {/*        points: 0*/}
            {/*    }, {*/}
            {/*        username: "opponent",*/}
            {/*        points: 0*/}
            {/*    }*/}
            {/*    ], state: {*/}
            {/*        phase: "IN_PROGRESS",*/}
            {/*        phaseEndTime: 0,*/}
            {/*        round: 0,*/}
            {/*        winner: "",*/}
            {/*        turn: "test",*/}
            {/*    }*/}

            {/*})} myFleet={[*/}
            {/*    new Ship(*/}
            {/*        new ShipType(5, "Carrier", 0),*/}
            {/*        new Coordinate(1, 1),*/}
            {/*        Orientation.VERTICAL),*/}
            {/*    new Ship(*/}
            {/*        new ShipType(4, "Battleship", 0),*/}
            {/*        new Coordinate(3, 7),*/}
            {/*        Orientation.VERTICAL),*/}
            {/*    new Ship(*/}
            {/*        new ShipType(3, "Cruiser", 0),*/}
            {/*        new Coordinate(6, 3),*/}
            {/*        Orientation.HORIZONTAL),*/}

            {/*]}/>*/}

            {/*<TileHitView hitShip={true}/>*/}
        </div>
    );
}


export default App;
