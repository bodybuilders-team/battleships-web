import * as React from "react";
import PageContent from "../Utils/PageContent";

/**
 * About component.
 */
function About() {

    const [error, setError] = React.useState<string | null>(null);

    return (
        <PageContent title={"About"} error={error}>
            { /*TODO: Add content here - about and other info about the app*/}
        </PageContent>
    );
}

export default About;
