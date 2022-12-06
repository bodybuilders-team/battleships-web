import TextField from "@mui/material/TextField";
import * as React from "react";

/**
 * The textfield for the username.
 */
export function UsernameTextField(props: { errors: any, onChange: (event: any) => void }) {
    return <TextField
        {...props.errors.username && {error: true, helperText: props.errors.username}}
        required
        fullWidth
        name="username"
        label="Username"
        margin="normal"
        onChange={props.onChange}
        autoComplete="username"
    />;
}
