import { Client } from "@stomp/stompjs"

export type ClientState = Client | undefined
export type TokenState = string | undefined

export interface ClientAction {
    type: string
    payload: ClientState
}

export interface TokenAction {
    type: string
    payload: TokenState
}

export interface State {
    client: ClientState
    accessToken: TokenState
}

export interface ClientProps {
    client: ClientState
}
