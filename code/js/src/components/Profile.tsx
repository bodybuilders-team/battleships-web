import * as React from "react";
import {useSession} from "../utils/Session";

/**
 * Profile component.
 */
function Profile() {
    const session = useSession();

    return (
        <div>
            <h1>Profile</h1>
            Username: {session.username}
        </div>
    );
}

export default Profile;
