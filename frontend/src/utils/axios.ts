import axios, { AxiosError, AxiosRequestConfig, AxiosResponse } from "axios"
import { AxiosCallback, AxiosDestination, AxiosFallback } from "../types/axios"

const baseURL = 'http://localhost:8080'

export const send = (
    to: AxiosDestination, 
    data: object, 
    callback: AxiosCallback, 
    fallback: AxiosFallback
) => {
    const config: AxiosRequestConfig = {
        url: to.url,
        method: to.method,
        data: data,
        baseURL: baseURL,
        withCredentials: true
    }
    axios(config)
        .then((res: AxiosResponse) => callback(res))
        .catch((err: Error | AxiosError) => {
            if (axios.isAxiosError(err)) {
                console.log(err.response)
                if (err.response) fallback(err.response)
            } else console.log(err)
    })
}
