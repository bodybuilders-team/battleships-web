import Typography from "@mui/material/Typography";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import ShipView from "../Shared/Ship/ShipView";
import {Orientation} from "../../../Domain/games/ship/Orientation";
import IconButton from "@mui/material/IconButton";
import {Add, Remove} from "@mui/icons-material";
import * as React from "react";
import {ShipType} from "../../../Domain/games/ship/ShipType";

const maxBoardOccupancyPercentage = 0.5;
const minShipQuantity = 0;
const maxShipQuantity = 5;

/**
 * Properties for ShipSelector component.
 *
 * @property shipTypes the ship types
 * @property setShipTypes callback to set the ship types
 * @property gridSize the grid size
 */
interface ShipSelectorProps {
    shipTypes: Map<ShipType, number>;
    setShipTypes: (shipTypes: Map<ShipType, number>) => void;
    gridSize: number;
}

/**
 * ShipSelector component.
 */
export default function ShipSelector({shipTypes, setShipTypes, gridSize}: ShipSelectorProps) {

    let totalTilesOccupied = 0;
    Array.from(shipTypes.entries()).forEach(([shipType, count]) => {
        totalTilesOccupied += shipType.size * count;
    });

    return (
        <Box>
            <Typography id="ships-selector" gutterBottom>
                Ships
            </Typography>
            <Grid container spacing={2} justifyContent={"center"}>
                {
                    Array.from(shipTypes.entries())
                        .map(([ship, quantity]) => {
                            return (
                                <Grid item key={ship.shipName}>
                                    <Box sx={{height: 200}}>
                                        <ShipView type={ship} orientation={Orientation.VERTICAL}/>
                                    </Box>
                                    <IconButton
                                        aria-label="add"
                                        color="primary"
                                        onClick={() => {
                                            if (quantity < maxShipQuantity)
                                                setShipTypes(new Map(shipTypes.set(ship, quantity + 1)));
                                        }}
                                        disabled={quantity >= maxShipQuantity || totalTilesOccupied + ship.size >=
                                            gridSize * gridSize * maxBoardOccupancyPercentage}
                                    >
                                        <Add/>
                                    </IconButton>
                                    <Typography id="ship-quantity-selector" gutterBottom>
                                        {quantity}
                                    </Typography>
                                    <IconButton
                                        aria-label="remove"
                                        color="primary"
                                        onClick={() => {
                                            if (quantity > minShipQuantity)
                                                setShipTypes(new Map(shipTypes.set(ship, quantity - 1)));
                                        }}
                                        disabled={quantity <= minShipQuantity}
                                    >
                                        <Remove/>
                                    </IconButton>
                                </Grid>
                            );
                        })
                }
            </Grid>
        </Box>
    );
}
