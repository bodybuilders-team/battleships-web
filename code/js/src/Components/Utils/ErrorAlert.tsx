import {Alert} from "@mui/material";
import * as React from "react";

/**
 * Alert component to display errors.
 *
 * @param props object containing the error message.
 */
export function ErrorAlert(props: { error: string | null }) {
    return <>{props.error && <Alert severity="error">{props.error}</Alert>}</>;
}
