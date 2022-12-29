import * as React from "react"
import {useEffect} from "react"

export function useMounted() {
    const isMounted = React.useRef(false)

    useEffect(() => {
        isMounted.current = true
        return () => {
            isMounted.current = false
        }
    }, [])

    return React.useCallback(() => isMounted.current, [])
}

export function useMountedSignal() {
    const controller = React.useRef<AbortController>(new AbortController())

    useEffect(() => {

        return () => {
            controller.current.abort()
        }
    }, [])

    return controller.current.signal
}