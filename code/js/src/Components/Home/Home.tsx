import * as React from "react";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import PlayArrowIcon from "@mui/icons-material/PlayArrow";
import {useNavigate} from "react-router-dom";

/**
 * Home component.
 */
function Home() {
    const navigate = useNavigate();

    return (
        <div>
            <h1>Home</h1>
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Box sx={{mt: 1}}>
                    <Button
                        fullWidth
                        size="large"
                        variant="contained"
                        sx={{mt: 3, mb: 2}}
                        startIcon={<PlayArrowIcon/>}
                        color="primary"
                        onClick={() => {
                            navigate('/gameplay')
                        }}
                    >
                        Play
                    </Button>
                </Box>
            </Box>
        </div>
    );
}

export default Home;
