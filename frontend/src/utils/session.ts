import { slice, store } from "../app/store"
import { AxiosDestination, AxiosCallback, AxiosFallback } from "../types/axios"
import { TokenPayload } from "../types/token"
import { sendAsync } from "./axios"

export const getTokenPayload = (): TokenPayload => {
    const token = getAccessToken()
    return token ? JSON.parse(atob(token.split(".")[1])) : undefined;
}

export const getAccessToken = () => {
    return store.getState().accessToken
}

export const setAccessToken = async () => {
    const to: AxiosDestination = {
        url: '/token',
        method: 'GET'
    }

    const callback: AxiosCallback = (res) => {
        store.dispatch(slice.actions.setAccessToken(res.data))
    }
    const fallback: AxiosFallback = (res) => {
    }

    return await sendAsync(to, {}, callback, fallback)
}

export const removeAccessToken = () => {
    const to: AxiosDestination = {
        url: '/token',
        method: 'DELETE'
    }

    const callback: AxiosCallback = (res) => {
        store.dispatch(slice.actions.setAccessToken())
    }
    const fallback: AxiosFallback = (res) => {
    }

    sendAsync(to, {}, callback, fallback)
}
