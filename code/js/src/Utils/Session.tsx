import * as React from 'react';
import {createContext, useContext, useEffect, useState} from 'react';

/**
 * Holds the session data.
 *
 * @property username the username of the user
 * @property accessToken the access token of the user
 * @property refreshToken the refresh token of the user
 */
interface Session {
    username: string,
    accessToken: string,
    refreshToken: string,
}

/**
 * The manager for the session.
 *
 * @property loggedIn whether the user is logged in or not
 * @property session the session data
 * @property setSession sets the session data
 * @property clearSession clears the session data
 */
interface SessionManager {
    loggedIn: boolean,
    session: Session | null,
    setSession: (session: Session) => void
    clearSession: () => void
}

const SessionManagerContext = createContext<SessionManager>({
    loggedIn: false,
    session: null,
    setSession: () => {
    },
    clearSession: () => {
    }
});

const sessionStorageKey = 'session';

/**
 * Provides the session data to the children.
 *
 * @param children the children to render
 */
export function Auth({children}: { children: React.ReactNode }) {
    const [loggedIn, setLoggedIn] = useState(false);
    const [session, setSession] = useState<Session | null>(null);

    useEffect(() => {
        const sessionJson = localStorage.getItem(sessionStorageKey);
        if (!sessionJson)
            return;

        const session = JSON.parse(sessionJson);

        setLoggedIn(true);
        setSession(session);
    }, []);

    return (
        <SessionManagerContext.Provider
            value={{
                loggedIn,
                session,
                setSession: (session: Session) => {
                    setLoggedIn(true);
                    setSession(session);

                    localStorage.setItem(sessionStorageKey, JSON.stringify(session));
                },
                clearSession: () => {
                    localStorage.removeItem('session');

                    setLoggedIn(false);
                    setSession(null);
                }
            }}>
            {children}
        </SessionManagerContext.Provider>
    );
}

/**
 * Returns the session data.
 *
 * @return the session data
 */
export function useSession(): Session | null {
    return useContext(SessionManagerContext).session;
}

/**
 * Returns the session manager.
 *
 * @return the session manager
 */
export function useSessionManager(): SessionManager {
    return useContext(SessionManagerContext);
}

/**
 * Checks whether the user is logged in or not.
 *
 * @return true if the user is logged in, false otherwise
 */
export function useLoggedIn(): boolean {
    return useSessionManager().loggedIn;
}
