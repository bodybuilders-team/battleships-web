import {Alert} from "@mui/material"
import * as React from "react"

/**
 * Properties of the ErrorAlert component.
 *
 * @property error the error to display
 */
interface ErrorAlertProps {
    error: string | null
}

/**
 * Alert component to display errors.
 *
 * @param props object containing the error message.
 */
export default function ErrorAlert({error}: ErrorAlertProps) {
    return (
        <>
            {error && <Alert severity="error">{error}</Alert>}
        </>
    )
}
