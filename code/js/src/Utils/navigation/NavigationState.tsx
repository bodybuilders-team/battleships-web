import * as React from "react";
import {useContext, useRef} from "react";

/**
 * NavigationState interface.
 *
 * @property links map of links
 * @property setLinks setter for links
 */
interface NavigationState {
    links: Map<string, string>;
    setLinks: (links: Map<string, string>) => void;
}

const NavigationStateContext = React.createContext<NavigationState>({
    links: new Map<string, string>(),
    setLinks: () => {
    }
});

/**
 * Provides the navigation state to the children.
 *
 * @param children the children to render
 */
export function NavigationState({children}: { children: React.ReactNode }) {
    // Needs to be useRef to avoid re-rendering the children, also can't
    // be useMemo because useMemo may reset the state.
    const links = useRef(new Map<string, string>()).current;

    return (
        <NavigationStateContext.Provider value={{
            links, setLinks: (newLinks) => {
                links.clear();
                newLinks.forEach((value, key) => {
                    links.set(key, value)
                });
            }
        }}>
            {children}
        </NavigationStateContext.Provider>
    );
}

/**
 * Returns the navigation state.
 *
 * @returns the navigation state
 */
export function useNavigationState() {
    return useContext(NavigationStateContext);
}
