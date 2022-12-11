import  {useEffect, useState} from "react";
import TimerIcon from "@mui/icons-material/Timer";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import * as React from "react";

export function useCountdownTimer(finalTime: number, onTimeUp?: () => void, updateInterval: number = 1000) {
    const finalTimeDate = new Date(finalTime).getTime()

    console.log(finalTime)

    const [currentTime, setCurrentTime] = useState(finalTimeDate - new Date().getTime())

    useEffect(() => {
        const id = setInterval(() => {
            const newTime = finalTimeDate - new Date().getTime()
            if (newTime <= 0) {
                clearInterval(id)
                setCurrentTime(0)
            } else {
                setCurrentTime(newTime)
            }
        }, updateInterval)

        return () => clearInterval(id)
    }, [finalTime])

    // parse currentTime to days, hours, minutes and seconds
    const days = Math.floor(currentTime / (1000 * 60 * 60 * 24))
    const hours = Math.floor((currentTime % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60))
    const minutes = Math.floor((currentTime % (1000 * 60 * 60)) / (1000 * 60))
    const seconds = Math.floor((currentTime % (1000 * 60)) / 1000)

    return {currentTime, days, hours, minutes, seconds}
}

interface CountdownTimerProps {
    finalTime: number
    onTimeUp?: () => void
    criticalLastSeconds?: number
}

export function CountdownTimer({finalTime, onTimeUp, criticalLastSeconds = 10}: CountdownTimerProps) {
    const {currentTime, minutes, seconds} = useCountdownTimer(finalTime, onTimeUp )

    return (
        <Box sx={{display: 'flex', alignItems: 'center'}}>
            <TimerIcon/>
            <Typography variant="h6" component="div"
                        sx={{color: currentTime <= criticalLastSeconds * 1000 ? "red" : undefined}}>
                {minutes}:{seconds}
            </Typography>
        </Box>
    );
}
