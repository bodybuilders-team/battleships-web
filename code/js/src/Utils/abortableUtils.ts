import * as React from "react";
import to from "./await-to";

/**
 * An error that is thrown when a component is unmounted
 *
 */
export class AbortedError extends Error {
    constructor() {
        super("Aborted");
    }
}

/**
 * A side effect that catches the AbortedError and does not rethrow it
 *
 * @param effect The side effect to run
 * @param dependencies The dependencies of the side effect
 */
export function useAbortableEffect(
    effect: () => void | (() => void),
    dependencies?: React.DependencyList
) {
    React.useEffect(() => {
        try {
            effect();
        } catch (e) {
            if (!(e instanceof AbortedError))
                throw e;
        }
    }, dependencies);
}

/**
 * Sets a timeout that is aborted when the signal is aborted
 *
 * @param callback The callback to run
 * @param delay The delay in milliseconds
 * @param signal The signal to abort on
 * @param resolveIfAborted Whether to resolve the promise if the signal is aborted
 * @param args The arguments to pass to the callback
 */
export function setAbortableTimeout(callback: (...args: any[]) => void,
                                    delay?: number,
                                    signal?: AbortSignal, resolveIfAborted?: boolean, ...args: any[]) {

    if (signal)
        signal.addEventListener("abort", abortHandler);

    const timeoutId = setTimeout(callbackWrapper, delay);

    function callbackWrapper() {
        if (signal)
            signal.removeEventListener("abort", abortHandler);

        callback(...args);
    }

    function abortHandler() {
        clearTimeout(timeoutId);

        if (resolveIfAborted)
            callback(...args);
    }

    return timeoutId;
}

/**
 * Delays for a specified amount of time
 *
 * @param delay The delay in milliseconds
 * @param signal The signal to abort on
 */
export function delay(delay: number, signal?: AbortSignal) {
    return new Promise((resolve, reject) => {
        function callback() {
            if (signal && signal.aborted) {
                reject(new AbortedError());
                return
            }

            resolve(undefined);
        }

        setAbortableTimeout(callback, delay, signal, true);
    });
}

/**
 * Runs a promise and aborts it if the signal is aborted
 *
 * @param promise The promise to run
 */
export async function abortableTo<T, E = Error>(promise: Promise<T>): Promise<[E, null] | [null, T]> {
    const data = await to<T, E>(promise)

    const [err] = data;

    if (err && err instanceof AbortedError)
        throw err;

    return data;
}