import { Method, AxiosResponse } from "axios"

export const baseURL = 'http://localhost:8080'

export type AxiosDestination = {
    url: string,
    method: Method
}

export type AxiosCallback = (res: AxiosResponse) => any
export type AxiosFallback = (response: AxiosResponse) => any
