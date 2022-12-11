import * as React from "react";
import {useContext, useMemo} from "react";

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
    // Check this, basically we switched to useRef because we didn't
    // want to re-render the whole app when the links changed
    const links = useMemo(() => new Map<string, string>(), []);

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
