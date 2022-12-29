import to from "../await-to"

/**
 * An error that is thrown when a component is unmounted.
 */
export class AbortedError extends Error {
    constructor() {
        super("Aborted")
    }
}

/**
 * Sets a timeout that is aborted when the signal is aborted.
 *
 * @param callback the callback to run
 * @param delay the delay in milliseconds
 * @param signal the signal to abort on
 * @param resolveIfAborted whether to resolve the promise if the signal is aborted
 * @param args the arguments to pass to the callback
 *
 * @returns the timeout id
 */
export function setAbortableTimeout(
    callback: (...args: any[]) => void,
    delay?: number,
    signal?: AbortSignal,
    resolveIfAborted?: boolean, ...args: any[]
): NodeJS.Timeout {
    if (signal)
        signal.addEventListener("abort", abortHandler)

    const timeoutId = setTimeout(callbackWrapper, delay)

    /**
     * The callback wrapper that calls the callback and removes the abort handler.
     */
    function callbackWrapper() {
        if (signal)
            signal.removeEventListener("abort", abortHandler)

        callback(...args)
    }

    /**
     * The abort handler that aborts the timeout and resolves the promise if the signal is aborted.
     */
    function abortHandler() {
        clearTimeout(timeoutId)

        if (resolveIfAborted)
            callback(...args)
    }

    return timeoutId
}

/**
 * Delays for a specified amount of time.
 *
 * @param delay the delay in milliseconds
 * @param signal the signal to abort on
 *
 * @returns a promise that resolves when the delay is over
 */
export function delay(delay: number, signal?: AbortSignal): Promise<void> {
    return new Promise((resolve, reject) => {
        function callback() {
            if (signal && signal.aborted) {
                reject(new AbortedError())
                return
            }

            resolve(undefined)
        }

        setAbortableTimeout(callback, delay, signal, true)
    })
}

/**
 * Runs a promise and aborts it if the signal is aborted.
 *
 * @param promise the promise to run
 */
export async function abortableTo<T, E = Error>(promise: Promise<T>): Promise<[E, null] | [null, T]> {
    const data = await to<T, E>(promise)
    const [err] = data

    if (err && err instanceof AbortedError)
        throw err

    return data
}