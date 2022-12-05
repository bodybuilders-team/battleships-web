import * as React from "react";
import {useState} from "react";

/**
 * NavigationState interface.
 *
 * @interface NavigationState
 * @property {Map<string, string>} links - Map of links.
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
export function NavigationStateProvider({children}: { children: React.ReactNode }) {
    const [links, setLinks] = useState<Map<string, string>>(new Map<string, string>());

    return (
        <NavigationStateContext.Provider value={{links, setLinks}}>
            {children}
        </NavigationStateContext.Provider>
    );
}

/**
 * Returns the navigation state.
 */
export function useNavigationState() {
    return React.useContext(NavigationStateContext);
}