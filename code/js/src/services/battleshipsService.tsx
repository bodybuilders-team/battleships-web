import {Problem} from "./utils/Problem";

const apiUrl = 'http://localhost:8080';

function fetchSiren()

/**
 * Registers a new user.
 *
 * @param email the email of the user
 * @param username the username of the user
 * @param password the password of the user
 *
 * @returns the response of the request to the API
 */
export async function register(email: string, username: string, password: string) {
    const res = await fetch(`${apiUrl}/users`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({email, username, password})
    });

    if (!res.ok)
        throw new Problem(await res.json());

    return await res.json();
}

/**
 * Logs in a user.
 *
 * @param username the username of the user
 * @param password the password of the user
 *
 * @returns the response of the request to the API
 */
export async function login(username: string, password: string) {
    const res = await fetch(`${apiUrl}/users/login`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({username, password})
    });

    if (!res.ok)
        throw new Problem(await res.json());

    return await res.json();
}
