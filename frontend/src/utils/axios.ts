import axios, { AxiosError, AxiosRequestConfig, AxiosRequestHeaders, AxiosResponse } from "axios"
import { AxiosCallback, AxiosDestination, AxiosFallback, baseURL } from "../types/axios"
import { getAccessToken } from "./session"

const makeConfig = (
    to: AxiosDestination, 
    data: object
): AxiosRequestConfig => {
    const accessToken = getAccessToken()
    const headers: AxiosRequestHeaders = { authorization: accessToken ? "Bearer " + accessToken : "" }

    return {
        url: to.url,
        method: to.method,
        headers: headers,
        data: data,
        baseURL: baseURL,
        withCredentials: true
    }
}

export const send = (
    to: AxiosDestination, 
    data: object, 
    callback: AxiosCallback, 
    fallback: AxiosFallback
) => {
    axios(makeConfig(to, data))
        .then((res: AxiosResponse) => callback(res))
        .catch((err: Error | AxiosError) => {
            if (axios.isAxiosError(err)) {
                console.log(err.response)
                if (err.response) fallback(err.response)
            } else console.log(err)
    })
}

export const sendAsync = async (
    to: AxiosDestination, 
    data: object, 
    callback: AxiosCallback, 
    fallback: AxiosFallback
) => {
    const res = await axios(makeConfig(to, data))
    return (res.status === 200) ? callback(res) : fallback(res)
}
