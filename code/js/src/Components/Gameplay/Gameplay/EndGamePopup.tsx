import * as React from "react";
import {Dialog, DialogActions, DialogContent, DialogTitle} from "@mui/material";
import DialogContentText from '@mui/material/DialogContentText';
import Button from "@mui/material/Button";
import {HomeRounded, RefreshRounded} from "@mui/icons-material";
import {useNavigate} from "react-router-dom";

/**
 * Properties for the EndGamePopup component.
 *
 * @param winningPlayer the player who won the game
 * @param cause the cause of the end of the game
 */
interface EndGamePopupProps {
    winningPlayer: WinningPlayer;
    cause: EndGameCause;
}

/**
 * The cause of the end of the game.
 *
 * @property DESTRUCTION the game ended because a player's fleet was destroyed
 * @property RESIGNATION the game ended because a player resigned
 * @property TIMEOUT the game ended because a player took too long
 */
enum EndGameCause {
    DESTRUCTION,
    RESIGNATION,
    TIMEOUT
}

/**
 * The cause of the end of the game.
 *
 * @property YOU you won
 * @property OPPONENT the opponent won
 */
enum WinningPlayer {
    YOU,
    OPPONENT,
    NONE
}

/**
 * The EndGamePopup component.
 */
export default function EndGamePopup({winningPlayer, cause,}: EndGamePopupProps) {
    const navigate = useNavigate();

    return (
        <Dialog open={true} onClose={undefined}>
            <DialogTitle>
                {
                    (
                        winningPlayer === WinningPlayer.YOU
                            ? "You won!"
                            : "You lost!"
                    )
                    + " " + (cause === EndGameCause.DESTRUCTION
                            ? "Fleet destroyed"
                            : cause === EndGameCause.RESIGNATION
                                ? "Resigned"
                                : "Timeout"
                    )
                }
            </DialogTitle>

            <DialogContent>
                <DialogContentText>
                    {
                        // TODO:
                    }
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button
                    fullWidth
                    size="large"
                    variant="contained"
                    sx={{mt: 3, mb: 2}}
                    startIcon={<HomeRounded/>}
                    color="primary"
                    onClick={() => navigate("/")}
                >
                    Home
                </Button>
                // play again
                <Button
                    fullWidth
                    size="large"
                    variant="contained"
                    sx={{mt: 3, mb: 2}}
                    startIcon={<RefreshRounded/>}
                    color="primary"
                    onClick={() => navigate("/gameplay-menu")}
                >
                    Play Again
                </Button>
            </DialogActions>
        </Dialog>
    );
}
