const apiUrl = 'http://localhost:8080';

/**
 * Registers a new user.
 *
 * @param email the email of the user
 * @param username the username of the user
 * @param password the password of the user
 *
 * @returns the response of the request to the API
 */
export async function register(email, username, password) {
    const res = await fetch(`${apiUrl}/users`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({email, username, password})
    });

    if (!res.ok)
        throw await res.json();

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
export async function login(username, password) {
    const res = await fetch(`${apiUrl}/users/login`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({username, password})
    });

    if (!res.ok)
        throw await res.json();

    return await res.json();
}
