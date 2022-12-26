import * as React from "react";
import {useAbortableEffect} from "./abortableUtils";

export function useMounted() {
    const isMounted = React.useRef(false);

    useAbortableEffect(() => {
        isMounted.current = true;
        return () => {
            isMounted.current = false;
        };
    }, []);

    return React.useCallback(() => isMounted.current, []);
}

export function useMountedSignal() {
    const controller = React.useRef<AbortController>(new AbortController());

    useAbortableEffect(() => {

        return () => {
            controller.current.abort();
        };
    }, []);

    return controller.current.signal;
}