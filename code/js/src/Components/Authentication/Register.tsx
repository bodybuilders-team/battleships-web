import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import {useNavigate} from 'react-router-dom';
import {useForm} from '../../Utils/formUtils';
import {validateEmail, validatePassword, validateUsername} from '../../Utils/validateFields';
import to from '../../Utils/await-to';
import * as usersService from '../../Services/users/UsersService';
import {useSessionManager} from "../../Utils/Session";
import {handleError} from "../../Services/utils/fetchSiren";
import {ErrorAlert} from "../Utils/ErrorAlert";
import PageContent from "../Utils/PageContent";


/**
 * Register component.
 */
function Register() {
    const navigate = useNavigate();
    const sessionManager = useSessionManager();
    const [formError, setFormError] = React.useState<string | null>(null);

    const {handleSubmit, handleChange, errors} = useForm({
        initialValues: {email: '', username: '', password: ''},
        validate: (values) => {
            return {
                email: validateEmail(values.email),
                username: validateUsername(values.username),
                password: validatePassword(values.password)
            };
        },
        onSubmit: async (values) => {
            const {email, username, password} = values;
            const [err, res] = await to(usersService.register("http://localhost:8080/users", email, username, password));

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
        <PageContent error={formError}>
            <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}><LockOutlinedIcon/></Avatar>
            <Typography component="h1" variant="h5">Register</Typography>
            <Box component="form" noValidate onSubmit={handleSubmit} sx={{mt: 3}}>
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <TextField
                            {...errors.username && {error: true, helperText: errors.username}}
                            required
                            fullWidth
                            name="username"
                            label="Username"
                            onChange={handleChange}
                            autoComplete="username"
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            {...errors.email && {error: true, helperText: errors.email}}
                            required
                            fullWidth
                            name="email"
                            label="Email Address"
                            type="email"
                            onChange={handleChange}
                            autoComplete="email"
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            {...errors.password && {error: true, helperText: errors.password}}
                            required
                            fullWidth
                            name="password"
                            label="Password"
                            type="password"
                            onChange={handleChange}
                            autoComplete="new-password"
                        />
                    </Grid>
                </Grid>
                <ErrorAlert error={formError}/>
                <Button
                    fullWidth
                    type='submit'
                    variant="contained"
                    sx={{mt: 3, mb: 2}}
                >
                    Register
                </Button>
                <Grid container justifyContent="flex-end">
                    <Grid item>
                        <Link onClick={() => {
                            navigate('/login')
                        }} component='button' variant="body2">
                            Already have an account? Login
                        </Link>
                    </Grid>
                </Grid>
            </Box>
        </PageContent>
    );
}

export default Register;
