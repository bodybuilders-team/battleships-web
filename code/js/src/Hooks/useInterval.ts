import {useEffect} from "react";

/**
 * Hook that calls a function every interval
 * @param callback the function to call, if it returns true, the interval is cleared
 * @param delay the delay between calls
 * @param dependencies the dependencies of the hook
 */
export function useInterval(callback: () => Promise<boolean> | boolean | void, delay: number, dependencies?: any[]) {

    useEffect(() => {
        let cancelled = false
        let timeoutId: NodeJS.Timeout | undefined = undefined

        async function tick() {
            const shouldStop = await callback()

            if (!cancelled && !shouldStop)
                timeoutId = setTimeout(tick, delay)
        }

        tick()

        return () => {
            cancelled = true
            if (timeoutId !== undefined)
                clearTimeout(timeoutId)
        };
    }, dependencies)
}

/**
 * Hook that calls a function after a delay
 * @param callback the function to call
 * @param delay the delay
 * @param dependencies the dependencies of the hook
 */
export function useTimeout(callback: () => void, delay: number, dependencies: any[]) {

    useEffect(() => {
        const timeoutId = setTimeout(callback, delay)

        return () => {
            clearTimeout(timeoutId)
        };
    }, dependencies)
}