import * as React from "react"
import {useContext, useRef} from "react"
import {Rels} from "./Rels";

/**
 * NavigationState interface.
 *
 * @property links map of links
 * @property setLinks setter for links
 */
interface NavigationState {
    links: Map<string, string>

    clearGameLinks(): void;
}

const NavigationStateContext = React.createContext<NavigationState>({
    links: new Map<string, string>(),
    clearGameLinks: () => {

    }
})

/**
 * Provides the navigation state to the children.
 *
 * @param children the children to render
 */
export function NavigationState({children}: { children: React.ReactNode }) {
    // Needs to be useRef to avoid re-rendering the children, also can't
    // be useMemo because useMemo may reset the state.
    const links = useRef(new Map<string, string>()).current

    return (
        <NavigationStateContext.Provider value={{
            links,
            clearGameLinks: () => {
                //Clear all game links
                const oldLinks = new Map(links)
                oldLinks.forEach((value, key) => {
                    if (key.startsWith(Rels.GAME) ||
                        key.startsWith(Rels.JOIN_GAME)
                    )
                        links.delete(key)

                    switch (key) {
                        case Rels.DEPLOY_FLEET:
                        case Rels.FIRE_SHOTS:
                        case Rels.GET_OPPONENT_FLEET:
                        case Rels.GET_OPPONENT_SHOTS:
                        case Rels.GET_MY_FLEET:
                        case Rels.GET_MY_SHOTS:
                        case Rels.LEAVE_GAME:
                        case Rels.GET_MY_BOARD:
                            links.delete(key)
                    }
                })
            }
        }}>
            {children}
        </NavigationStateContext.Provider>
    )
}

/**
 * Returns the navigation state.
 *
 * @returns the navigation state
 */
export function useNavigationState() {
    return useContext(NavigationStateContext)
}
