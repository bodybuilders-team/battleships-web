import * as React from "react";
import GameplayMenu from "./GameplayMenu/GameplayMenu";
import BoardSetup from "./BoardSetup/BoardSetup";
import {defaultShipTypes} from "../../Domain/games/ship/ShipType";
import GameConfiguration from "./GameConfiguration/GameConfiguration";

/**
 * Gameplay component.
 */
function Gameplay() {
    // TODO: all code here is temporary, just to test the gameplay menu, board setup and game configuration components
    // TODO: These constants does not have any meaning, they are just to test the components
    const [game, setGame] = React.useState(false);
    const [quickGame, setQuickGame] = React.useState(false);

    return (
        <div>
            {
                !game
                    ? <GameplayMenu
                        onQuickPlay={() => {
                            setGame(true);
                            setQuickGame(true)
                        }}
                        onCreateGame={() => setGame(true)}
                        onSearchGame={() => setGame(true)}/>
                    : (
                        quickGame
                            ? <BoardSetup boardSize={10} ships={defaultShipTypes}/>
                            : <GameConfiguration/>
                    )
            }
        </div>
    );
}

export default Gameplay;
