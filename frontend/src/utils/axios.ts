import axios, { AxiosError, AxiosRequestConfig, AxiosResponse, Method } from "axios"

const baseURL = 'http://localhost:8080'

export interface sendTo {
    url: string,
    method: Method
}

export interface Callback {
    (res: AxiosResponse): any
}
export interface Fallback {
    (response: AxiosResponse): any
}

export const send = (
    to: sendTo, 
    data: object, 
    callback: Callback, 
    fallback: Fallback
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
