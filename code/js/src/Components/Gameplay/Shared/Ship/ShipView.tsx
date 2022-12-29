import Box from "@mui/material/Box"
import * as React from "react"
import {tileSize} from "../Board/Tile"
import {ShipType} from "../../../../Domain/games/ship/ShipType"
import {Orientation} from "../../../../Domain/games/ship/Orientation"

/**
 * Properties for the ShipView component.
 *
 * @property shipType the type of the ship
 * @property orientation the orientation of the ship
 * @property onClick the function to be called when the ship is clicked
 */
interface ShipViewProps {
    shipType: ShipType
    orientation: Orientation
    onClick?: () => void
}

/**
 * ShipView component.
 */
export default function ShipView({shipType, orientation}: ShipViewProps) {
    const shipImage = `ship_${shipType.shipName.toLowerCase()}_${orientation === Orientation.VERTICAL ? "v" : "h"}.png`
    const shipImageSrc = require(`../../../../Assets/${shipImage}`).default

    return (
        <Box
            sx={{
                width: (tileSize * (orientation === Orientation.HORIZONTAL ? shipType.size : 1)),
                height: (tileSize * (orientation === Orientation.VERTICAL ? shipType.size : 1)),
                backgroundImage: `url(${shipImageSrc})`,
                backgroundSize: shipType.shipName == "Carrier" ?
                    (orientation == Orientation.HORIZONTAL ? "80% 100%" : "100% 80%")
                    : "contain",
                backgroundRepeat: "no-repeat",
                backgroundPosition: "center",
            }}
        />
    )
}
