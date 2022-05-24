import { Dispatch } from "@reduxjs/toolkit"
import { Client } from "@stomp/stompjs"
import React, { useReducer } from "react"
import { connect } from "react-redux"
import { Navigate, useParams } from "react-router-dom"
import { ClientProps, clientSlice } from "../app/stompStore"
import { Chat, Info } from "../components/Chatroom/Chat"
import ChatForm from "../components/Chatroom/ChatForm"
import { stompClient } from "../utils/stomp"

export interface ChatDTO {
    chat: string
    sender?: string
}

interface ChatAction {
    type: string
    payload: ChatDTO
}

export type ChatDispatch = React.Dispatch<ChatAction>

export enum ChatActionType {
    CHAT = "chat", INFO = "info"
}

interface Props extends ClientProps{
    setClient: (id: string, dispatch: ChatDispatch) => void
}

const reducer = (state: JSX.Element[], action: ChatAction) => {
    switch (action.type) {
        case ChatActionType.CHAT:
            return state.concat(<Chat {...action.payload} />)
        case ChatActionType.INFO:
            return state.concat(<Info {...action.payload} />)
        default:
            return state
    }
}

const Chatroom = (props: Props) => {
    const id = useParams().id
    const [chats, dispatch] = useReducer(reducer, [])

    if (!id) return <Navigate to="/" />

    window.onclick = () => { if (!props.client) props.setClient(id, dispatch) }

    return (
        <>
            <div>id: {id}</div>
            <div id="chats">{chats}</div>
            <ChatForm id={id} />
        </>
    )
}

const mapStateToProps = (state: Client) => {
    return { client: state }
}

const mapDispatchToProps = (reduxDispatch: Dispatch) => {
    return { 
        setClient: (id: string, dispatch: ChatDispatch) => {
            reduxDispatch(clientSlice.actions.create(stompClient(id, dispatch)))
        }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Chatroom)
