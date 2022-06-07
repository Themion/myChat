import { Client } from "@stomp/stompjs"

export type ClientState = Client | undefined
export type TokenState = string | undefined

export type ClientAction = {
    type: string
    payload: ClientState
}

export type TokenAction = {
    type: string
    payload: TokenState
}

export type State = {
    client: ClientState
    accessToken: TokenState
}

export type ClientProps = {
    client: ClientState
}
