import { slice, store } from "../app/store"
import { AxiosDestination, AxiosCallback, AxiosFallback } from "../types/axios"
import { TokenPayload } from "../types/token"
import { sendSync } from "./axios"

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
        // console.log(res)
    }

    return await sendSync(to, {}, callback, fallback)
}
