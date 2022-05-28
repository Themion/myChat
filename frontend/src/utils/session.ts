import { slice, store } from "../app/store"
import { Callback, Fallback, send, sendTo } from "./axios"

interface Payload {
    sub: string,
    exp: number,
    iat: number
}

export const getTokenPayload = () => {
    const token = getAccessToken()
    if (!token) return
    return JSON.parse(atob(token.split(".")[1])) as Payload
}

export const getAccessToken = () => {
    return store.getState().accessToken
}

export const setAccessToken = () => {
    const to: sendTo = {
        url: '/token',
        method: 'GET'
    }

    const callback: Callback = (res) => {
        store.dispatch(slice.actions.setAccessToken(res.data))
    }
    const fallback: Fallback = (res) => {
        console.log(res)
    }

    send(to, {}, callback, fallback)
}

export const refreshAccessToken = () => {

}