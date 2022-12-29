import TextField from "@mui/material/TextField"
import * as React from "react"

/**
 * Properties of the EmailTextField component.
 *
 * @property errors the errors
 * @property onChange the change handler
 */
interface EmailTextFieldProps {
    errors: any
    onChange: (event: any) => void
}

/**
 * The textfield for the email.
 */
export function EmailTextField({errors, onChange}: EmailTextFieldProps) {
    return (
        <TextField
            {...errors.email && {error: true, helperText: errors.email}}
            required
            fullWidth
            name="email"
            label="Email Address"
            type="email"
            margin="normal"
            onChange={onChange}
            autoComplete="email"
        />
    )
}
