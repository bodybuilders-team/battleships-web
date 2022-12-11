import * as React from 'react';
import {useState} from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import {useNavigate} from 'react-router-dom';
import {useForm} from '../Shared/useForm';
import to from "../../../Utils/await-to";
import {useSessionManager} from "../../../Utils/Session";
import {validatePassword, validateUsername} from '../Shared/validateFields';
import {handleError} from "../../../Services/utils/fetchSiren";
import ErrorAlert from "../../Shared/ErrorAlert";
import PageContent from "../../Shared/PageContent";
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService";
import {useNavigationState} from "../../../Utils/navigation/NavigationState";
import {Rels} from "../../../Utils/navigation/Rels";
import {throwError} from "../../../Services/utils/errorUtils";
import {UsernameTextField} from "../Shared/UsernameTextField";
import {PasswordTextField} from "../Shared/PasswordTextField";

/**
 * Login component.
 */
export default function Login() {
    const navigate = useNavigate();
    const navigationState = useNavigationState();

    const battleshipsService = useBattleshipsService();
    const sessionManager = useSessionManager();

    const [formError, setFormError] = useState<string | null>(null);

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
            const [err, res] = await to(battleshipsService.usersService.login(username, password));

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

            navigationState.setLinks(battleshipsService.links);
            navigate('/');
        }
    });

    return (
        <PageContent>
            <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}><LockOutlinedIcon/></Avatar>
            <Typography component="h1" variant="h5">Sign in</Typography>
            <Box component="form" noValidate onSubmit={handleSubmit} sx={{mt: 1}}>
                <UsernameTextField errors={errors} onChange={handleChange}/>
                <PasswordTextField errors={errors} onChange={handleChange}/>
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