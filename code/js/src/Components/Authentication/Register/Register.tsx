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
import {validateEmail, validatePassword, validateUsername} from '../Shared/validateFields';
import to from '../../../Utils/await-to';
import {useSessionManager} from "../../../Utils/Session";
import {handleError} from "../../../Services/utils/fetchSiren";
import ErrorAlert from "../../Shared/ErrorAlert";
import PageContent from "../../Shared/PageContent";
import {useNavigationState} from "../../../Utils/navigation/NavigationState";
import {useBattleshipsService} from "../../../Services/NavigationBattleshipsService";
import {Rels} from "../../../Utils/navigation/Rels";
import {throwError} from "../../../Services/utils/errorUtils";
import {PasswordTextField} from "../Shared/PasswordTextField";
import {UsernameTextField} from "../Shared/UsernameTextField";
import {EmailTextField} from "../Shared/EmailTextField";

/**
 * Register component.
 */
export default function Register() {
    const navigate = useNavigate();
    const navigationState = useNavigationState();

    const battleshipsService = useBattleshipsService();
    const sessionManager = useSessionManager();

    const [formError, setFormError] = useState<string | null>(null);

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
            const [err, res] = await to(battleshipsService.usersService.register(email, username, password));

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
            <Typography component="h1" variant="h5">Register</Typography>
            <Box component="form" noValidate onSubmit={handleSubmit} sx={{mt: 3}}>
                <UsernameTextField errors={errors} onChange={handleChange}/>
                <EmailTextField errors={errors} onChange={handleChange}/>
                <PasswordTextField errors={errors} onChange={handleChange}/>
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
