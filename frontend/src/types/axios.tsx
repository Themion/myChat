import { Method, AxiosResponse } from "axios"

export interface AxiosDestination {
    url: string,
    method: Method
}

export type AxiosCallback = (res: AxiosResponse) => any
export type AxiosFallback = (response: AxiosResponse) => any
