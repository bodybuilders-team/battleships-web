import Typography from "@mui/material/Typography"
import {Slider} from "@mui/material"
import Box from "@mui/material/Box"
import * as React from "react"

/**
 * GameConfigSlider properties.
 *
 * @param id the id of the slider
 * @param label the label of the slider
 * @param defaultValue the default value of the slider
 * @param step the step of the slider
 * @param min the minimum value of the slider
 * @param max the maximum value of the slider
 * @param onValueChange the callback when the value of the slider changes
 */
interface GameConfigSliderProps {
    id: string
    label: string
    defaultValue: number
    step: number
    min: number
    max: number
    onValueChange: (value: number) => void
}

/**
 * GameConfigSlider component.
 */
export default function GameConfigSlider({
                                             id,
                                             label,
                                             defaultValue,
                                             step,
                                             min,
                                             max,
                                             onValueChange
                                         }: GameConfigSliderProps) {
    return (
        <Box>
            <Typography id={id} gutterBottom>
                {label}
            </Typography>
            <Slider
                defaultValue={defaultValue}
                aria-labelledby={id}
                valueLabelDisplay="auto"
                step={step}
                marks
                min={min}
                max={max}
                onChange={(event, value) => {
                    onValueChange(value as number)
                }}
            />
        </Box>
    )
}
