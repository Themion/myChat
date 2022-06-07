import { Method, AxiosResponse } from "axios"

export type AxiosDestination = {
    url: string,
    method: Method
}

export type AxiosCallback = (res: AxiosResponse) => any
export type AxiosFallback = (response: AxiosResponse) => any
