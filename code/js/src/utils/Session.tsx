import * as React from 'react'
import {createContext, useContext, useEffect, useState} from 'react'

type Session = {
    loggedIn: boolean,
    username: string,
    accessToken: string,
    refreshToken: string,
    setSession: (username: string, accessToken: string, refreshToken: string) => void
    clearSession: () => void
}
const SessionContext = createContext<Session>(null)

export function Auth({children}: { children: React.ReactNode }) {
    // Since we use change the session data all at the same time, maybe we should use a single state object?
    const [loggedIn, setLoggedIn] = useState(false)
    const [username, setUsername] = useState(null)
    const [accessToken, setAccessToken] = useState(null)
    const [refreshToken, setRefreshToken] = useState(null)

    useEffect(() => {
        const session = JSON.parse(localStorage.getItem('session'))

        if (session) {
            setLoggedIn(true)
            setUsername(session.username)
            setAccessToken(session.accessToken)
            setRefreshToken(session.refreshToken)
        }
    }, [])

    function setSession(username: string, accessToken: string, refreshToken: string) {
        setLoggedIn(true)
        setUsername(username)
        setAccessToken(accessToken)
        setRefreshToken(refreshToken)

        localStorage.setItem('session', JSON.stringify({
            username,
            accessToken,
            refreshToken
        }))
    }

    function clearSession() {
        localStorage.setItem('session', null)

        setLoggedIn(false)
        setUsername(null)
        setAccessToken(null)
        setRefreshToken(null)
    }

    return (
        <SessionContext.Provider value={{loggedIn, username, accessToken, refreshToken, setSession, clearSession}}>
            {children}
        </SessionContext.Provider>
    )
}

export function useSession() {
    return useContext(SessionContext)
}

export function useLoggedIn() {
    return useSession().loggedIn
}
