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
    const shipImage = `ship_${type.shipName.toLowerCase()}_${orientation === Orientation.VERTICAL ? "v" : "h"}.png`;
    const shipImageSrc = require(`../../../../Assets/${shipImage}`).default;

    return (
        <Box
            sx={{
                width: (tileSize * (orientation === Orientation.HORIZONTAL ? type.size : 1)),
                height: (tileSize * (orientation === Orientation.VERTICAL ? type.size : 1)),
                margin: 'auto',
                marginTop: '10px',
            }}
        >
            <img src={shipImageSrc} alt={"ship"}/>
        </Box>
    );
}

export default ShipView;
