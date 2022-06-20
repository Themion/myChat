import { Client } from "@stomp/stompjs"

export type ClientState = Client | undefined
export type TokenState = string | undefined

export type Action<T> = {
    type: string
    payload: T
}

export type State = {
    client: ClientState
    accessToken: TokenState
}

export type ClientProps = {
    client: ClientState
}
