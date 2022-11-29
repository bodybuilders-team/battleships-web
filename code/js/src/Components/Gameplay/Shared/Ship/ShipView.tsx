import Box from "@mui/material/Box";
import * as React from "react";
import {tileSize} from "../Board/Tile";
import {ShipType} from "../../../../Domain/games/ship/ShipType";
import {Orientation} from "../../../../Domain/games/ship/Orientation";

/**
 * Visual representation of a ship.
 *
 * @param type the type of the ship
 * @param orientation the orientation of the ship
 */
function ShipView({type, orientation}: { type: ShipType, orientation: Orientation }) {
    return (
        <Box
            sx={{
                width: (tileSize * (orientation === Orientation.HORIZONTAL ? type.size : 1)),
                height: (tileSize * (orientation === Orientation.VERTICAL ? type.size : 1)),
                backgroundColor: 'darkgray',
                margin: 'auto',
                border: '1px solid black',
                marginTop: '10px',
            }}
        >
            {/*TODO: Add image*/}
        </Box>
    );
}

export default ShipView;
