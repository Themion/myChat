import { configureStore, createSlice } from "@reduxjs/toolkit";
import { ClientAction, State, TokenAction } from "../types/redux";

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
