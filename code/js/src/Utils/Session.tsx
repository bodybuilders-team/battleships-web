import * as React from 'react';
import {createContext, useContext, useState} from 'react';

/**
 * Holds the session data.
 *
 * @property username the username of the user
 * @property accessToken the access token of the user
 * @property refreshToken the refresh token of the user
 * @property userHomeLink the user home link
 */
export interface Session {
    readonly username: string,
    readonly accessToken: string,
    readonly refreshToken: string,
    readonly userHomeLink: string
}

/**
 * The manager for the session.
 *
 * @property session the session data
 * @property setSession sets the session data
 * @property clearSession clears the session data
 */
export interface SessionManager {
    readonly session: Session | null,
    readonly  setSession: (session: Session) => void
    readonly  clearSession: () => void
}

const SessionManagerContext = createContext<SessionManager>({
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
    const [session, setSession] = useState<Session | null>(() => {
        const sessionJson = localStorage.getItem(sessionStorageKey);
        if (!sessionJson)
            return;

        return JSON.parse(sessionJson);
    });

    return (
        <SessionManagerContext.Provider
            value={{
                session,
                setSession: (session: Session) => {
                    setSession(session);

                    localStorage.setItem(sessionStorageKey, JSON.stringify(session));
                },
                clearSession: () => {
                    localStorage.removeItem('session');

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
    return useSessionManager().session != null;
}
