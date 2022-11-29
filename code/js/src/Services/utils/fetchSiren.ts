import {SirenEntity, sirenMediaType} from "./siren/SirenEntity";
import {Problem, problemMediaType} from "./Problem";

/**
 * Fetches a Siren entity from the given URL.
 *
 * @param input the URL to fetch the Siren entity from
 * @param token the token to send with the request
 * @param method the HTTP method to use
 * @param body the body to send with the request
 *
 * @return a promise that resolves to the Siren entity
 */
export async function fetchSiren<T>(
    input: RequestInfo | URL,
    token?: string | null,
    method?: string,
    body?: BodyInit | null,
): Promise<SirenEntity<T>> {
    const res = await fetch(input, {
        method: method,
        headers: {
            'Accept': `${sirenMediaType}, ${problemMediaType}`,
            'Content-Type': 'application/json',
            'Authorization': token ? `Bearer ${token}` : ''
        },
        body: body
    });

    if (!res.ok) {
        if (res.headers.get('Content-Type') !== problemMediaType)
            throw new Error(`Unexpected response type: ${res.headers.get('Content-Type')}`);

        throw new Problem(await res.json());
    }

    if (res.headers.get('Content-Type') !== sirenMediaType)
        throw new Error(`Unexpected response type: ${res.headers.get('Content-Type')}`);

    const json = await res.json();

    return new SirenEntity<T>(json);
}

/**
 * Sends a GET request to the specified link.
 *
 * @param input the link to send the request to
 *
 * @return the result of the request
 */
export function get<T>(input: RequestInfo | URL): Promise<SirenEntity<T>> {
    return fetchSiren<T>(input);
}

/**
 * Sends a GET request to the specified link with a token in the header.
 *
 * @param input the link to send the request to
 * @param token the token to send in the header
 *
 * @return the result of the request
 */
export function getWithAuth<T>(input: RequestInfo | URL, token: string): Promise<SirenEntity<T>> {
    return fetchSiren<T>(input, token);
}

/**
 * Sends a POST request to the specified link with the specified body.
 *
 * @param input the link to send the request to
 * @param body the body to send in the request
 *
 * @return the result of the request
 */
export function post<T>(input: RequestInfo | URL, body: BodyInit | null): Promise<SirenEntity<T>> {
    return fetchSiren<T>(input, null, 'POST', body);
}

/**
 * Sends a POST request to the specified link with the specified body and a token in the header.
 *
 * @param link the link to send the request to
 * @param token the token to send in the header
 * @param body the body to send in the request, if null, an empty request is sent
 *
 * @return the result of the request
 */
export function postWithAuth<T>(
    link: RequestInfo | URL,
    token: string,
    body?: BodyInit | null
): Promise<SirenEntity<T>> {
    return fetchSiren<T>(link, token, 'POST', body);
}
