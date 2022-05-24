import { configureStore, createSlice } from "@reduxjs/toolkit";
import { Client } from "@stomp/stompjs";

export interface ClientProps {
    client: Client
}

interface CreateAction {
    type: string
    payload: Client
}

type ClientState = Client | undefined

export const clientSlice = createSlice({
    name: "clientReducer",
    initialState: undefined as ClientState, 
    reducers: {
        create: (state, action: CreateAction) => {
            state = action.payload
            state.activate()
            return state
        }
    }
})

export const clientStore = configureStore({reducer: clientSlice.reducer})
