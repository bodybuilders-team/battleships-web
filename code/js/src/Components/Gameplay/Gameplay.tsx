import * as React from "react";
import GameplayMenu from "./GameplayMenu/GameplayMenu";
import BoardSetup from "./BoardSetup/BoardSetup";
import {ShipType} from "../../Domain/games/ship/ShipType";

/**
 * Gameplay component.
 */
function Gameplay() {
    const [game, setGame] = React.useState(false);
    // TODO: all code here is temporary, just to test the gameplay menu and board setup

    return (
        <div>
            {
                !game
                    ? <GameplayMenu onQuickPlay={() => setGame(true)}/>
                    : <BoardSetup boardSize={10} ships={[
                        new ShipType(5, "Carrier", 50),
                        new ShipType(4, "Battleship", 40),
                        new ShipType(3, "Cruiser", 30),
                        new ShipType(3, "Submarine", 30),
                        new ShipType(2, "Destroyer", 20)
                    ]}/>
            }
        </div>
    );
}

export default Gameplay;
