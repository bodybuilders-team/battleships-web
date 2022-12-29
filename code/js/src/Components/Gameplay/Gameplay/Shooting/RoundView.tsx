import Box from "@mui/material/Box"
import * as React from "react"
import {Divider} from "@mui/material"
import Typography from "@mui/material/Typography"

/**
 * Properties for the RoundView component.
 *
 * @param round the round to display
 */
interface RoundViewProps {
    round: number
}

/**
 * The round view for the gameplay.
 */
export default function RoundView({round}: RoundViewProps) {
    return (
        <Box sx={{
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center',
            alignItems: 'center',
            borderRadius: 1,
            border: '2px solid',
            borderColor: 'black',
        }}>
            <Typography variant="h6" component="div">
                Round
            </Typography>
            <Divider sx={{width: '100%', backgroundColor: 'black', height: '2px'}}/>
            <Typography variant="h6" component="div">
                {round}
            </Typography>
        </Box>
    )
}
