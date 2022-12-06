import TextField from "@mui/material/TextField";
import * as React from "react";

/**
 * The textfield for the email.
 */
export function EmailTextField(props: { errors: any, onChange: (event: any) => void }) {
    return <TextField
        {...props.errors.email && {error: true, helperText: props.errors.email}}
        required
        fullWidth
        name="email"
        label="Email Address"
        type="email"
        margin="normal"
        onChange={props.onChange}
        autoComplete="email"
    />;
}
