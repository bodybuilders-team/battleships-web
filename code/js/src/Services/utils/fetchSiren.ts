import {SirenEntity, sirenMediaType} from "../media/siren/SirenEntity"
import {Problem, problemMediaType} from "../media/Problem"
import * as React from "react"
import {API_ENDPOINT} from "../BattleshipsService"
import to from "../../Utils/await-to"

/**
 * Fetches a Siren entity from the given URL.
 *
 * @param input the URL to fetch the Siren entity from
 * @param token the token to send with the request
 * @param method the HTTP method to use
 * @param body the body to send with the request
 * @param signal the signal to abort the request
 *
 * @return a promise that resolves to the Siren entity
 */
export async function fetchSiren<T>(
    input: RequestInfo | URL,
    token?: string,
    method?: string,
    body?: BodyInit,
    signal?: AbortSignal
): Promise<SirenEntity<T>> {

    const headers: any = {
        'Accept': `${sirenMediaType}, ${problemMediaType}`,
        'Content-Type': 'application/json',
    }

    if (token)
        headers['Authorization'] = `Bearer ${token}`

    const [err, res] = await to(fetch(input, {
        method: method,
        headers,
        body: body,
        signal: signal,
    }))

    if (err)
        throw new NetworkError(err.message)

    if (!res.ok) {
        if (res.headers.get('Content-Type') !== problemMediaType)
            throw new UnexpectedResponseError(`Unexpected response type: ${res.headers.get('Content-Type')}`)

        throw new Problem(await res.json())
    }

    if (res.headers.get('Content-Type') !== sirenMediaType)
        throw new UnexpectedResponseError(`Unexpected response type: ${res.headers.get('Content-Type')}`)

    const json = await res.json()

    return new SirenEntity<T>(json)
}

/**
 * Handles an API error response.
 *
 * @param err the error
 * @param setError the error setter
 */
export function handleError(
    err: Error | Problem,
    setError: React.Dispatch<React.SetStateAction<string | null>>
) {
    if (err instanceof Problem)
        setError(err.title)
    else
        setError(err.message)
}

/**
 * Sends a GET request to the specified link.
 *
 * @param input the link to send the request to
 * @param signal the signal to abort the request
 *
 * @return the result of the request
 */
export function get<T>(input: RequestInfo | URL, signal?: AbortSignal): Promise<SirenEntity<T>> {
    return fetchSiren<T>(API_ENDPOINT + input, undefined, undefined, undefined, signal)
}

/**
 * Sends a GET request to the specified link with a token in the header.
 *
 * @param input the link to send the request to
 * @param token the token to send in the header
 * @param signal the signal to abort the request
 *
 * @return the result of the request
 */
export function getWithAuth<T>(input: RequestInfo | URL, token: string, signal?: AbortSignal): Promise<SirenEntity<T>> {
    return fetchSiren<T>(API_ENDPOINT + input, token, undefined, undefined, signal)
}

/**
 * Sends a POST request to the specified link with the specified body.
 *
 * @param input the link to send the request to
 * @param body the body to send in the request
 * @param signal the signal to abort the request
 *
 * @return the result of the request
 */
export function post<T>(input: RequestInfo | URL, body: BodyInit, signal?: AbortSignal): Promise<SirenEntity<T>> {
    return fetchSiren<T>(API_ENDPOINT + input, undefined, 'POST', body, signal)
}

/**
 * Sends a POST request to the specified link with the specified body and a token in the header.
 *
 * @param link the link to send the request to
 * @param token the token to send in the header
 * @param body the body to send in the request, if undefined, an empty request is sent
 * @param signal the signal to abort the request
 *
 * @return the result of the request
 */
export function postWithAuth<T>(
    link: RequestInfo | URL,
    token: string,
    body?: BodyInit,
    signal?: AbortSignal
): Promise<SirenEntity<T>> {
    return fetchSiren<T>(API_ENDPOINT + link, token, 'POST', body, signal)
}

/**
 * An error that occurs if there is a network error.
 */
export class NetworkError extends Error {
    constructor(message: string) {
        super(message)
    }
}

/**
 * An error that occurs if the response is not a Siren entity.
 */
export class UnexpectedResponseError extends Error {
    constructor(message: string) {
        super(message)
    }
}
