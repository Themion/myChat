import { createSlice, configureStore } from "@reduxjs/toolkit"
import { Action, ClientState, State, TokenState } from "../types/redux"

export const slice = createSlice({
    name: "clientReducer",
    initialState: {} as State, 
    reducers: {
        setClient: (state, action: Action<ClientState>) => {
            if (action.payload) {
                state.client = action.payload
                state.client.activate()
            } else {
                state.client?.deactivate()
                state.client = undefined
            }
            return state
        },
        setAccessToken: (state, action: Action<TokenState>) => {
            return { ...state, accessToken: action.payload }
        }
    }
})

export const store = configureStore({reducer: slice.reducer})

export const getAccessToken = () => {
    return store.getState().accessToken
}
