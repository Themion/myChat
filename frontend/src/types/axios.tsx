import { Method, AxiosResponse } from "axios"

export interface AxiosDestination {
    url: string,
    method: Method
}

export interface AxiosCallback {
    (res: AxiosResponse): any
}
export interface AxiosFallback {
    (response: AxiosResponse): any
}
