import * as React from "react";
import Button from "@mui/material/Button";
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import AddIcon from '@mui/icons-material/Add';
import SearchIcon from '@mui/icons-material/Search';
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";

/**
 * GameplayMenu component.
 */
function GameplayMenu({
                          onQuickPlay,
                          onCreateGame,
                          onSearchGame
                      }: { onQuickPlay: () => void, onCreateGame: () => void, onSearchGame: () => void }) {
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
                        onQuickPlay();
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
                        onCreateGame();
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
                        onSearchGame();
                    }}
                >
                    Search Game
                </Button>
            </Box>
        </Box>
    );
}

export default GameplayMenu;
