// TODO: To be implemented

const apiUrl = 'http://localhost:8080';

export async function register(email, username, password) {
    const response = await fetch(`${apiUrl}/users`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({email, username, password})
    })

    if (!response.ok)
        throw await response.json();

    return await response.json()
}

export async function login(username, password) {
    const response = await fetch(`${apiUrl}/users/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({username, password})
    })

    if (!response.ok)
        throw await response.json();

    return await response.json()
}
