import { slice, store } from "../app/store"
import { AxiosDestination, AxiosCallback, AxiosFallback } from "../types/axios"
import { TokenPayload } from "../types/token"
import { send } from "./axios"

export const getTokenPayload = () => {
    const token = getAccessToken()
    if (!token) return
    return JSON.parse(atob(token.split(".")[1])) as TokenPayload
}

export const getAccessToken = () => {
    return store.getState().accessToken
}

export const setAccessToken = () => {
    const to: AxiosDestination = {
        url: '/token',
        method: 'GET'
    }

    const callback: AxiosCallback = (res) => {
        store.dispatch(slice.actions.setAccessToken(res.data))
    }
    const fallback: AxiosFallback = (res) => {
        console.log(res)
    }

    send(to, {}, callback, fallback)
}

export const refreshAccessToken = () => {

}