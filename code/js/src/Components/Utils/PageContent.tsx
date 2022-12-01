import * as React from "react";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import {ErrorAlert} from "./ErrorAlert";

/**
 * Component that wraps the content of a page.
 *
 * @param props object containing the title of the page and the error message
 */
function PageContent(props: { title?: string, error: string | null, children: React.ReactNode }) {
    return (
        <Container component="main" maxWidth="xs">
            <h1>{props.title}</h1>
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <ErrorAlert error={props.error}/>
                {props.children}
            </Box>
        </Container>
    );
}

export default PageContent;
