import Box from "@mui/material/Box";
import * as React from "react";
import {tileSize} from "../Board/Tile";
import {ShipType} from "../../../../Domain/games/ship/ShipType";
import {Orientation} from "../../../../Domain/games/ship/Orientation";

type ShipViewProps = {
    type: ShipType, orientation: Orientation,
    onClick?: () => void
}

/**
 * Visual representation of a ship.
 *
 * @param type the type of the ship
 * @param orientation the orientation of the ship
 * @param onClick the function to be called when the ship is clicked
 */
function ShipView({type, orientation, onClick}: ShipViewProps) {
    const shipImage = `ship_${type.shipName.toLowerCase()}_${orientation === Orientation.VERTICAL ? "v" : "h"}.png`;
    const shipImageSrc = require(`../../../../Assets/${shipImage}`).default;

    function test() {
        return {}
    }

    return (
        <Box
            sx={{
                width: (tileSize * (orientation === Orientation.HORIZONTAL ? type.size : 1)),
                height: (tileSize * (orientation === Orientation.VERTICAL ? type.size : 1)),
            }}
            onClick={onClick}
        >
            <img src={shipImageSrc} alt={type.shipName} style={(() => {
                const obj: React.CSSProperties = {
                    pointerEvents: "none",
                    display: "block",
                    margin: "auto"
                }

                if (orientation === Orientation.VERTICAL)
                    obj.maxHeight = "100%"
                else
                    obj.maxWidth = "100%"

                return obj
            })()}


            />
        </Box>
    );
}

export default ShipView;
