import * as React from "react";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import ErrorAlert from "./ErrorAlert";

/**
 * Properties of the PageContent component.
 *
 * @property title the title of the page
 * @property error the error message
 * @property children the children to render
 */
interface PageContentProps {
    title?: string;
    error?: string | null;
    children: React.ReactNode;
}

/**
 * Component that wraps the content of a page.
 */
export default function PageContent({title, error, children}: PageContentProps) {
    return (
        <Container maxWidth="xs">
            <h1>{title}</h1>
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <ErrorAlert error={error ?? null}/>
                {children}
            </Box>
        </Container>
    );
}
