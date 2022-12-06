import TextField from "@mui/material/TextField";
import * as React from "react";

/**
 * The textfield for the password.
 */
export function PasswordTextField(props: { errors: any, onChange: (event: any) => void }) {
    return <TextField
        {...props.errors.password && {error: true, helperText: props.errors.password}}
        required
        fullWidth
        name="password"
        margin="normal"
        label="Password"
        type="password"
        onChange={props.onChange}
        autoComplete="new-password"
    />;
}
