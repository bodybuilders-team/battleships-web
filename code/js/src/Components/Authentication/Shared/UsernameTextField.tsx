import TextField from "@mui/material/TextField"
import * as React from "react"

/**
 * Properties of the UsernameTextField component.
 *
 * @property errors the errors
 * @property onChange the change handler
 */
interface UsernameTextFieldProps {
    errors: any
    onChange: (event: any) => void
}

/**
 * The textfield for the username.
 */
export function UsernameTextField({errors, onChange}: UsernameTextFieldProps) {
    return (
        <TextField
            {...errors.username && {error: true, helperText: errors.username}}
            required
            fullWidth
            name="username"
            label="Username"
            margin="normal"
            onChange={onChange}
            autoComplete="username"
        />
    )
}
