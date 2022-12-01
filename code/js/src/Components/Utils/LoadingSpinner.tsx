import * as React from "react";
import {CircularProgress} from "@mui/material";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";

/**
 * A loading spinner that rotates infinitely.
 * Useful for indicating that a process is running.
 *
 * @param text the text to be shown below the spinner
 */
function LoadingSpinner({text}: { text: string }) {
    return (
        <Box sx={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
        }}>
            <CircularProgress/>
            <Typography variant="h6">{text}</Typography>
        </Box>
    );
}

export default LoadingSpinner;
