import {ExitToAppRounded} from "@mui/icons-material";
import Button from "@mui/material/Button";
import * as React from "react";

/**
 * Properties for the LeaveGameButton component.
 *
 * @param onClick the function to be called when the button is clicked
 */
interface LeaveGameButtonProps {
    onClick: () => void;
}


/**
 * The button that allows the player to leave the game.
 */
export default function LeaveGameButton({onClick}: LeaveGameButtonProps) {
    return (
        <Button
            size="large"
            variant="contained"
            sx={{mt: 3, mb: 2}}
            startIcon={<ExitToAppRounded/>}
            color="primary"
            onClick={onClick}
        >
            Leave Game
        </Button>
    );
}
