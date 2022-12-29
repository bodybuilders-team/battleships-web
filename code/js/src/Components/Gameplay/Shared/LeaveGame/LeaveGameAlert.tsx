import * as React from "react"
import {Dialog, DialogActions, DialogContent, DialogTitle} from "@mui/material"
import DialogContentText from "@mui/material/DialogContentText"
import Button from "@mui/material/Button"
import {ExitToAppRounded, PlayArrowRounded} from "@mui/icons-material"

/**
 * Properties for the LeaveGameAlert component.
 *
 * @param open whether the alert is open
 * @param onLeave the function to call when the user leaves the game
 * @param onStay the function to call when the user stays in the game
 */
interface LeaveGameAlertProps {
    open: boolean
    onLeave: () => void
    onStay: () => void
}

/**
 * An AlertDialog that asks the user if he wants to leave the game.
 */
export default function LeaveGameAlert({open, onLeave, onStay}: LeaveGameAlertProps) {
    return (
        <Dialog open={open} onClose={onStay} maxWidth={"xs"}>
            <DialogTitle>Leave game</DialogTitle>
            <DialogContent>
                <DialogContentText>
                    Are you sure you want to leave the game? You will lose the game if you leave now.
                </DialogContentText>
            </DialogContent>
            <DialogActions sx={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                justifyContent: "center",
            }}>
                <Button
                    fullWidth
                    size="large"
                    variant="contained"
                    sx={{mt: 3, mb: 2}}
                    startIcon={<PlayArrowRounded/>}
                    color="primary"
                    onClick={onStay}
                >
                    Keep Playing
                </Button>
                <Button
                    fullWidth
                    size="large"
                    variant="contained"
                    sx={{mt: 3, mb: 2}}
                    startIcon={<ExitToAppRounded/>}
                    color="primary"
                    onClick={onLeave}
                >
                    Leave Game
                </Button>
            </DialogActions>
        </Dialog>
    )
}
