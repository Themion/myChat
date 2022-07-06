import { getAccessToken, slice, store } from "../app/store"
import { AxiosDestination, AxiosCallback, AxiosFallback } from "../types/axios"
import { TokenPayload } from "../types/token"
import { sendAsync } from "./axios"

const usernameStorage = 'username'

export const getTokenPayload = (): TokenPayload => {
    const token = getAccessToken()
    return token ? JSON.parse(atob(token.split(".")[1])) : undefined;
}

export const setTokens = async () => {
    const to: AxiosDestination = { url: '/token', method: 'GET' }
    const callback: AxiosCallback = (res) => store.dispatch(slice.actions.setAccessToken(res.data))
    const fallback: AxiosFallback = (res) => store.dispatch(slice.actions.setAccessToken())

    return await sendAsync(to, {}, callback, fallback)
}

export const removeTokens = async () => {
    const to: AxiosDestination = { url: '/token', method: 'DELETE' }
    const callback: AxiosCallback = (res) => store.dispatch(slice.actions.setAccessToken())
    const fallback: AxiosFallback = (res) => console.log(res)

    return await sendAsync(to, {}, callback, fallback)
}

export const setUsername = (username: string) => {
    sessionStorage.setItem(usernameStorage, username)
}

export const getUsername = () => {
    return sessionStorage.getItem(usernameStorage)
}
