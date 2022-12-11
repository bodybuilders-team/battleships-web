import {useEffect, useState} from "react";

/**
 * Hook that returns the current time.
 *
 * @param finalTime the final time
 * @param onTimeUp the callback to call when the time is up
 * @param updateInterval the interval to update the time
 */
export function useCountdownTimer(finalTime: number, onTimeUp?: () => void, updateInterval: number = 1000) {
    const finalTimeDate = new Date(finalTime).getTime();
    const [currentTime, setCurrentTime] = useState(finalTimeDate - new Date().getTime());

    useEffect(() => {
        const id = setInterval(() => {
            const newTime = finalTimeDate - new Date().getTime();

            if (newTime <= 0) {
                clearInterval(id);
                setCurrentTime(0);
            } else
                setCurrentTime(newTime);

        }, updateInterval);

        return () => clearInterval(id);
    }, [finalTime]);

    // Parse currentTime to days, hours, minutes and seconds
    const days = Math.floor(currentTime / (1000 * 60 * 60 * 24));
    const hours = Math.floor((currentTime % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const minutes = Math.floor((currentTime % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((currentTime % (1000 * 60)) / 1000);

    return {currentTime, days, hours, minutes, seconds};
}
