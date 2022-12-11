import * as React from "react";
import TimerIcon from "@mui/icons-material/Timer";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import {useCountdownTimer} from "./useCountdownTimer";

/**
 * Properties for the CountdownTimer component.
 *
 * @property finalTime the final time
 * @property onTimeUp the callback to call when the time is up
 * @property criticalLastSeconds the number of seconds to consider the timer critical
 */
interface CountdownTimerProps {
    finalTime: number;
    onTimeUp?: () => void;
    criticalLastSeconds?: number;
}

/**
 * A countdown timer.
 */
export function CountdownTimer({finalTime, onTimeUp, criticalLastSeconds = 10}: CountdownTimerProps) {
    const {currentTime, minutes, seconds} = useCountdownTimer(finalTime, onTimeUp);

    return (
        <Box sx={{display: 'flex', alignItems: 'center'}}>
            <TimerIcon/>
            <Typography
                variant="h6"
                component="div"
                sx={{color: currentTime <= criticalLastSeconds * 1000 ? "red" : undefined}}
            >
                {minutes}:{seconds}
            </Typography>
        </Box>
    );
}
