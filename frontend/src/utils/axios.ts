import axios, { AxiosError, AxiosRequestConfig, AxiosResponse } from "axios"
import { AxiosCallback, AxiosDestination, AxiosFallback } from "../types/axios"

const baseURL = 'http://localhost:8080'

const makeConfig = (
    to: AxiosDestination, 
    data: object
): AxiosRequestConfig => {
    return {
        url: to.url,
        method: to.method,
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

export const sendSync = async (
    to: AxiosDestination, 
    data: object, 
    callback: AxiosCallback, 
    fallback: AxiosFallback
) => {
    const res = await axios(makeConfig(to, data))
    if (res.status === 200) callback(res)
    else fallback(res)
}
