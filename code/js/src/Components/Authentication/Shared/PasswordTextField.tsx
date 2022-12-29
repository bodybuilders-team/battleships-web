import TextField from "@mui/material/TextField"
import * as React from "react"

/**
 * Properties of the PasswordTextField component.
 *
 * @property errors the errors
 * @property onChange the change handler
 */
interface PasswordTextFieldProps {
    errors: any
    onChange: (event: any) => void
}

/**
 * The textfield for the password.
 */
export function PasswordTextField({errors, onChange}: PasswordTextFieldProps) {
    return (
        <TextField
            {...errors.password && {error: true, helperText: errors.password}}
            required
            fullWidth
            name="password"
            margin="normal"
            label="Password"
            type="password"
            onChange={onChange}
            autoComplete="new-password"
        />
    )
}
