import * as React from "react"
import Button from "@mui/material/Button"

/**
 * Properties of the MenuButton component.
 *
 * @property title the title of the button
 * @property icon the icon of the button
 * @property onClick the click handler
 */
interface MenuButtonProps {
    title: string
    icon?: React.ReactNode
    onClick?: () => void
}

/**
 * A button for the application menus.
 */
export default function MenuButton({title, icon, onClick}: MenuButtonProps) {
    return (
        <Button
            fullWidth
            size="large"
            variant="contained"
            sx={{mt: 3, mb: 2}}
            startIcon={icon}
            color="primary"
            onClick={onClick}
        >
            {title}
        </Button>
    )
}
