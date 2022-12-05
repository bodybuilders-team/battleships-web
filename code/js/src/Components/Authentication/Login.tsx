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
import to from "../../Utils/await-to";
import {useSessionManager} from "../../Utils/Session";
import {validatePassword, validateUsername} from '../../Utils/validateFields';
import {handleError} from "../../Services/utils/fetchSiren";
import {ErrorAlert} from "../Utils/ErrorAlert";
import PageContent from "../Utils/PageContent";
import {useBattleshipsService} from "../../Services/NavigationBattleshipsService";
import {useNavigationState} from "../../Utils/NavigationStateProvider";
import {Rels} from "../../Services/utils/Rels";
import {throwError} from "../../Services/utils/errorUtils";

/**
 * Login component.
 */
function Login() {
    const navigate = useNavigate();
    const sessionManager = useSessionManager();
    const [formError, setFormError] = React.useState<string | null>(null);
    const navigationState = useNavigationState()

    const [battleshipsService, setBattleshipsService] = useBattleshipsService()

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
            const [err, res] = await to(battleshipsService.usersService.login(username, password))

            if (err) {
                handleError(err, setFormError);
                return;
            }

            if (res?.properties === undefined)
                throw new Error("Properties are undefined");

            sessionManager.setSession({
                username,
                accessToken: res.properties.accessToken,
                refreshToken: res.properties.refreshToken,
                userHomeLink: battleshipsService.links.get(Rels.USER_HOME)
                    ?? throwError("User home link is undefined")
            });

            navigationState.setLinks(battleshipsService.links)
            navigate('/');
        }
    });

    return (
        <PageContent>
            <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}><LockOutlinedIcon/></Avatar>
            <Typography component="h1" variant="h5">Sign in</Typography>
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
                <ErrorAlert error={formError}/>
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
        </PageContent>
    );
}

export default Login;
