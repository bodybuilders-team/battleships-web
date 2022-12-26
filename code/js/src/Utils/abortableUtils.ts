import * as React from "react";
import to from "./await-to";

export class AbortedError extends Error {
    constructor() {
        super("Aborted");
    }
}

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

export async function abortableTo<T, E = Error>(promise: Promise<T>): Promise<[E, null] | [null, T]> {
    const data = await to<T, E>(promise)

    const [err] = data;

    if (err && err instanceof AbortedError)
        throw err;

    return data;
}