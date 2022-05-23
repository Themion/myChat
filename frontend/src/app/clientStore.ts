import { configureStore, createSlice } from "@reduxjs/toolkit";
import { Client } from "@stomp/stompjs";
import { ChatDTO } from "../components/ChatForm";
import { stompClient } from "../utils/stomp";

interface PublishType {
    id: string
    data: ChatDTO
}

export interface CreateAction {
    type: string
    payload: string
}

export interface PublishAction {
    type: string
    payload: PublishType
}

type ClientState = Client | undefined

export const clientSlice = createSlice({
    name: "clientReducer",
    initialState: undefined as ClientState, 
    reducers: {
        create: (state, action: CreateAction) => {
            state = stompClient(action.payload)
            state.activate()
            return state
        },
        publish: (state, action: PublishAction) => {
            if (!state) return

            const { id, data } = action.payload
            state.publish({
                destination: `/ws/${id}`,
                body: JSON.stringify(data)
            })
        }
    }
})

export const clientStore = configureStore({reducer: clientSlice.reducer})
