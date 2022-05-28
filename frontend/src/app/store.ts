import { configureStore, createSlice } from "@reduxjs/toolkit";
import { Client } from "@stomp/stompjs";

type ClientState = Client | undefined
type TokenState = string | undefined

interface ClientAction {
    type: string
    payload: ClientState
}

interface TokenAction {
    type: string
    payload: TokenState
}

export interface ClientProps {
    client: ClientState
}

export interface State {
    client: ClientState
    accessToken: TokenState
}

export const slice = createSlice({
    name: "clientReducer",
    initialState: {} as State, 
    reducers: {
        setClient: (state, action: ClientAction) => {
            if (action.payload) {
                state.client = action.payload
                state.client.activate()
            }
            return state
        },
        setAccessToken: (state, action: TokenAction) => {
            return { ...state, accessToken: action.payload }
        }
    }
})

export const store = configureStore({reducer: slice.reducer})
