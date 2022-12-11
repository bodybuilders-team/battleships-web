import * as React from "react";
import {useState} from "react";
import {Box, Button, Card, CardContent, CardHeader, Divider, Grid, TextField} from '@mui/material';

/**
 * ProfileDetais component.
 */
export default function ProfileDetais() {
    const [values] = useState({ // TODO: To change later
        firstName: 'Elon',
        lastName: 'Musk',
        email: 'elon@musk.com',
        country: 'USA'
    });

    return (
        <form autoComplete="off" noValidate>
            <Card>
                <CardHeader
                    subheader="The information can be edited"
                    title="Profile"
                />
                <Divider/>
                <CardContent>
                    <Grid container spacing={3}>
                        <Grid item md={6} xs={12}>
                            <TextField
                                fullWidth
                                helperText="Please specify the first name"
                                label="First name"
                                name="firstName"
                                required
                                value={values.firstName}
                                variant="outlined"
                            />
                        </Grid>
                        <Grid item md={6} xs={12}>
                            <TextField
                                fullWidth
                                label="Last name"
                                name="lastName"
                                required
                                value={values.lastName}
                                variant="outlined"
                            />
                        </Grid>
                        <Grid item md={6} xs={12}>
                            <TextField
                                fullWidth
                                label="Email Address"
                                name="email"
                                required
                                value={values.email}
                                variant="outlined"
                            />
                        </Grid>
                        <Grid item md={6} xs={12}>
                            <TextField
                                fullWidth
                                label="Country"
                                name="country"
                                required
                                value={values.country}
                                variant="outlined"
                            />
                        </Grid>
                    </Grid>
                </CardContent>
                <Divider/>
                <Box sx={{
                    display: 'flex',
                    justifyContent: 'flex-end',
                    p: 2
                }}>
                    <Button color="primary" variant="contained">
                        Save details
                    </Button>
                </Box>
            </Card>
        </form>
    );
}
