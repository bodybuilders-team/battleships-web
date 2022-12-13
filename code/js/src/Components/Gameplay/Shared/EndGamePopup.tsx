import * as React from "react";
import {Dialog, Typography} from "@mui/material";
import Button from "@mui/material/Button";
import {HomeRounded, RefreshRounded} from "@mui/icons-material";
import {useNavigate} from "react-router-dom";
import Box from "@mui/material/Box";
import Avatar from "@mui/material/Avatar";

/**
 * Properties for the EndGamePopup component.
 *
 * @property open whether the popup is open
 * @property winningPlayer the player who won the game
 * @property cause the cause of the end of the game
 * @property playerInfo the player info
 * @property opponentInfo the opponent info
 */
interface EndGamePopupProps {
    open: boolean;
    winningPlayer: WinningPlayer;
    cause: EndGameCause;
    playerInfo: PlayerInfo;
    opponentInfo: PlayerInfo;
}

/**
 * Player info
 *
 * @property name the name of the player
 * @property points the points of the player
 * @property avatar the avatar of the player
 */
interface PlayerInfo {
    name: string;
    points: number;
    avatar?: string;
}

/**
 * The cause of the end of the game.
 *
 * @property DESTRUCTION the game ended because a player's fleet was destroyed
 * @property RESIGNATION the game ended because a player resigned
 * @property TIMEOUT the game ended because a player took too long
 */
export enum EndGameCause {
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
export enum WinningPlayer {
    YOU,
    OPPONENT,
    NONE
}

/**
 * Player info component.
 * Displays the name and avatar of a player.
 *
 * @param name the name of the player
 * @param avatar the avatar of the player
 */
function PlayerInfoCard({playerInfo: {name, avatar}}: { playerInfo: PlayerInfo }) {
    return (
        <Box sx={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            mr: 2,
            ml: 2
        }}>
            <Avatar
                sx={{
                    width: 60,
                    height: 80,
                }}
                variant={"square"}
                src={avatar}
            />
            <Typography sx={{mt: 1,}} variant="h6">{name}</Typography>
        </Box>
    );
}

/**
 * The EndGamePopup component.
 */
export default function EndGamePopup({open, winningPlayer, cause, playerInfo, opponentInfo}: EndGamePopupProps) {
    const navigate = useNavigate();

    return (
        <Dialog open={open} onClose={undefined}>
            <Box sx={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                justifyContent: "center",
                px: 4,
            }}>
                <Typography variant="h5">
                    {
                        (
                            winningPlayer === WinningPlayer.NONE ?
                                "Game Aborted" :
                                winningPlayer === WinningPlayer.YOU
                                    ? "You won!"
                                    : "You lost!"
                        )
                    }
                </Typography>

                <Typography sx={{fontSize: "0.7rem",}}>
                    {
                        "By " + (
                            (cause === EndGameCause.DESTRUCTION
                                    ? "fleet destruction"
                                    : cause === EndGameCause.RESIGNATION
                                        ? "resignation"
                                        : "timeout"
                            )
                        )
                    }
                </Typography>
                <Typography variant="h6">
                    {
                        (winningPlayer === WinningPlayer.YOU)
                            ? "+" + playerInfo.points + " points"
                            : "No points won"
                    }
                </Typography>

                <Box sx={{
                    display: "flex",
                    flexDirection: "row",
                    alignItems: "center",
                    justifyContent: "center",
                    mt: 2,
                }}>
                    <PlayerInfoCard playerInfo={playerInfo}/>
                    <PlayerInfoCard playerInfo={opponentInfo}/>
                </Box>
                <Box sx={{
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                    justifyContent: "center",
                    width: "100%",
                    mt: 2,
                }}>
                    <Button
                        size="large"
                        variant="contained"
                        sx={{
                            mb: 2,
                            maxWidth: "70%",
                            maxHeight: "35px",
                        }}
                        startIcon={<HomeRounded/>}
                        color="primary"
                        onClick={() => navigate("/")}
                    >
                        Home
                    </Button>

                    <Button
                        size="small"
                        variant="contained"
                        sx={{
                            mb: 2,
                            maxWidth: "70%",
                            maxHeight: "35px",
                        }}
                        startIcon={<RefreshRounded/>}
                        color="primary"
                        onClick={() => navigate("/gameplay-menu")}
                    >
                        Play Again
                    </Button>
                </Box>
            </Box>
        </Dialog>
    );
}
