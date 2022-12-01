import * as React from "react";
import Button from "@mui/material/Button";
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import AddIcon from '@mui/icons-material/Add';
import SearchIcon from '@mui/icons-material/Search';
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Matchmake from "../Matchmake/Matchmake";
import GameConfiguration from "../GameConfiguration/GameConfiguration";

/**
 * GameplayMenu component.
 */
function GameplayMenu() {

    const [matchmaking, setMatchmaking] = React.useState(false);
    const [creating, setCreating] = React.useState(false);

    if (matchmaking)
        return <Matchmake/>;
    else if (creating)
        return <GameConfiguration/>;

    return (
        <Box
            sx={{
                marginTop: 8,
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
            }}
        >
            <Typography sx={{mb: 3}} variant="h4">Gameplay Menu</Typography>
            <Box sx={{mt: 1}}>
                <Button
                    fullWidth
                    size="large"
                    variant="contained"
                    sx={{mt: 3, mb: 2}}
                    startIcon={<PlayArrowIcon/>}
                    color="primary"
                    onClick={() => {
                        setMatchmaking(true);
                    }}
                >
                    Quick Play
                </Button>
                <Button
                    fullWidth
                    size="large"
                    variant="contained"
                    sx={{mt: 3, mb: 2}}
                    startIcon={<AddIcon/>}
                    color="primary"
                    onClick={() => {
                        setCreating(true);
                    }}
                >
                    New Game
                </Button>
                <Button
                    fullWidth
                    size="large"
                    variant="contained"
                    sx={{mt: 3, mb: 2}}
                    startIcon={<SearchIcon/>}
                    color="primary"
                    onClick={() => {
                    }}
                >
                    Search Game
                </Button>
            </Box>
        </Box>
    );
}

export default GameplayMenu;
