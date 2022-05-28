import { slice, store } from "../app/store"
import { Callback, Fallback, send, sendTo } from "./axios"

export const accessTokenHeader = 'access-token'
export const refreshTokenHeader = 'refresh-token'
export type Token = string

export const getAccessToken = () => {
    return store.getState().accessToken
}

export const setAccessToken = () => {
    const to: sendTo = {
        url: '/token',
        method: 'GET'
    }

    const callback: Callback = (res) => {
        console.log(res.data)
        store.dispatch(slice.actions.setAccessToken(res.data))
    }
    const fallback: Fallback = (res) => {}

    send(to, {}, callback, fallback)
}
