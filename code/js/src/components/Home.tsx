import * as React from "react";
import {useLoggedIn} from "../utils/Session";

/**
 * Home component.
 */
function Home() {
    const loggedIn = useLoggedIn();

    return (
        <div>
            <h1>Home</h1>
            { /*TODO: Add content here - about and other info about the app*/}
            {loggedIn ? <p>Logged in</p> : <p>Not logged in</p>}
        </div>
    );
}

export default Home;