import * as React from "react"
import {useCallback, useEffect, useRef} from "react"

/**
 * Returns a boolean indicating whether the component is mounted.
 *
 * @returns whether the component is mounted
 */
export function useMounted(): () => boolean {
    const isMounted = React.useRef(false)

    useEffect(() => {
        isMounted.current = true
        return () => {
            isMounted.current = false
        }
    }, [])

    return useCallback(() => isMounted.current, [])
}

/**
 * Returns a signal that is aborted when the component is unmounted.
 *
 * @returns the abort signal
 */
export function useMountedSignal(): AbortSignal {
    const controller = useRef<AbortController>(new AbortController())

    useEffect(() => {
        return () => {
            controller.current.abort()
        }
    }, [])

    return controller.current.signal
}
