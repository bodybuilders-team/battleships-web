import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import {useNavigate} from 'react-router-dom';
import {useForm} from '../utils/formUtils';

import * as battleshipsService from '../services/battleshipsService';
import to from "../utils/await-to";
import {Alert} from "@mui/material";
import {useSession} from "../utils/Session";

const theme = createTheme();

function Login() {
    const navigate = useNavigate()
    const session = useSession()

    const [formError, setFormError] = React.useState(null);

    function validate(values) {
        const errors = {
            username: null,
            password: null,
        };

        errors.username = values.username ? null : "Username is required";
        errors.password = values.password ? null : "Password is required";

        return errors;
    }


    const {handleSubmit, handleChange, errors} = useForm({
        initialValues: {username: null, password: null},
        validate,
        onSubmit: async (values) => {
            const {username, password} = values;

            const [err, res] = await to(battleshipsService.login(username, password))

            if (err) {
                setFormError(err.title);
                return
            }

            session.setSession(
                username,
                res.properties.accessToken,
                res.properties.refreshToken
            )

            navigate('/')
        }
    });

    return (
        <ThemeProvider theme={theme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline/>
                <Box
                    sx={{
                        marginTop: 8,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >
                    <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
                        <LockOutlinedIcon/>
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Sign in
                    </Typography>
                    <Box component="form" noValidate onSubmit={handleSubmit} sx={{mt: 1}}>
                        <TextField
                            {...errors.username && {error: true, helperText: errors.username}}
                            margin="normal"
                            required
                            fullWidth
                            label="Username"
                            name="username"
                            onChange={handleChange}
                            autoComplete="username"
                        />
                        <TextField
                            {...errors.password && {error: true, helperText: errors.password}}
                            margin="normal"
                            required
                            fullWidth
                            name="password"
                            label="Password"
                            type="password"
                            onChange={handleChange}
                            autoComplete="current-password"
                        />
                        {formError && <Alert severity="error">{formError}</Alert>}
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{mt: 3, mb: 2}}
                        >
                            Sign In
                        </Button>
                        <Grid container>
                            <Grid item>
                                <Link onClick={() => {
                                    navigate('/register')
                                }} component='button' variant="body2">
                                    Don't have an account? Sign Up
                                </Link>
                            </Grid>
                        </Grid>
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    );
}

export default Login;