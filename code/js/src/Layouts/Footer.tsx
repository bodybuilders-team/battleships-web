import * as React from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';

/**
 * Footer component.
 */
function Footer() {
    return (
        <Box
            component="footer"
            sx={{
                py: 3,
                px: 2,
                mt: 'auto',
                backgroundColor: (theme) =>
                    theme.palette.mode === 'light'
                        ? theme.palette.grey[200]
                        : theme.palette.grey[800],
                borderTop: '1px solid',
                borderColor: (theme) =>
                    theme.palette.mode === 'light'
                        ? theme.palette.grey[300]
                        : theme.palette.grey[700],
            }}
        >
            <Container maxWidth="sm">
                <Typography variant="body1">
                    Made with ❤️ by group 3 of DAW@ISEL in 2022/2023<br/>
                    48089 André Páscoa<br/>
                    48280 André Jesus<br/>
                    48287 Nyckollas Brandão
                </Typography>
            </Container>
        </Box>
    );
}

export default Footer;
