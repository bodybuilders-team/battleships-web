import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import {useNavigate} from 'react-router-dom';
import {useForm} from '../../Utils/formUtils';
import * as usersService from '../../Services/users/UsersService';
import to from "../../Utils/await-to";
import {Alert} from "@mui/material";
import {useSessionManager} from "../../Utils/Session";
import {validatePassword, validateUsername} from '../../Utils/validations';
import {handleError} from "../../Services/utils/fetchSiren";

/**
 * Login component.
 */
function Login() {
    const navigate = useNavigate();
    const sessionManager = useSessionManager();
    const [formError, setFormError] = React.useState<string | null>(null);

    const {handleSubmit, handleChange, errors} = useForm({
        initialValues: {username: '', password: ''},
        validate: (values) => {
            return {
                username: validateUsername(values.username),
                password: validatePassword(values.password)
            };
        },
        onSubmit: async (values) => {
            const {username, password} = values;
            const [err, res] = await to(usersService.login("http://localhost:8080/users/login", username, password))

            if (err) {
                handleError(err, setFormError);
                return;
            }

            if (res?.properties === undefined)
                throw new Error("Properties are undefined");

            sessionManager.setSession({
                username,
                accessToken: res.properties.accessToken,
                refreshToken: res.properties.refreshToken
            });

            navigate('/');
        }
    });

    return (
        <Container component="main" maxWidth="xs">
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
    );
}

export default Login;
