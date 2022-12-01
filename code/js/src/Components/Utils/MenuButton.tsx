import * as React from "react";
import Button from "@mui/material/Button";

/**
 * A button for the application menus.
 *
 * @param props the properties of the button
 */
export function MenuButton(props: { title: string, icon?: React.ReactNode, onClick?: () => void }) {
    return (
        <Button
            fullWidth
            size="large"
            variant="contained"
            sx={{mt: 3, mb: 2}}
            startIcon={props.icon}
            color="primary"
            onClick={props.onClick}
        >
            {props.title}
        </Button>
    );
}
