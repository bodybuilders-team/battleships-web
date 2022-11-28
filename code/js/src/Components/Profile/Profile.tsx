import * as React from "react";
import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import {useSession} from "../../utils/Session";

/**
 * Profile component.
 */
function Profile() {
    const navigate = useNavigate();
    const session = useSession();

    useEffect(() => {
        if (!session)
            navigate('/login');

    }, [session]);

    if (!session)
        return <></>

    return (
        <div>
            <h1>Profile</h1>
            Username: {session.username}
        </div>
    );
}

export default Profile;
