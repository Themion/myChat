import { Dispatch } from "@reduxjs/toolkit"
import { Client } from "@stomp/stompjs"
import React, { FormEventHandler, useReducer } from "react"
import { connect } from "react-redux"
import { Navigate, useParams } from "react-router-dom"
import { clientSlice } from "../app/stompStore"
import { Chat, Info } from "../components/Chatroom/Chat"
import { stompClient } from "../utils/stomp"

export interface ChatDTO {
    chat: string
    sender?: string
}

export interface ChatAction {
    type: string
    payload: ChatDTO
}

type ChatReducer = (state: JSX.Element[], action: ChatAction) => JSX.Element[]

export enum ChatActionType {
    CHAT = "chat", INFO = "info"
}

interface Props {
    client: Client
    setClient: (id: string, dispatch: React.Dispatch<ChatAction>) => void
}

const reducer: ChatReducer = (state, action) => {
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

    window.onclick = () => { 
        if (!props.client) {
            props.setClient(id, dispatch)
        }
    }

    const onSubmit: FormEventHandler = (e) => {
        e.preventDefault()
        
        const chat = document.getElementById("chat") as HTMLTextAreaElement
        if (chat.value === "") return

        const data: ChatDTO = { chat: chat.value }
        if (props.client) props.client.publish({
            destination: `/ws/${id}`,
            body: JSON.stringify(data)
        })

        chat.value = ""
    }

    return (
        <>
            <div>id: {id}</div>
            <div id="chats">{chats}</div>
            <form onSubmit={onSubmit}>
                <textarea id="chat"></textarea>
                <button type="submit">보내기</button>
            </form>
        </>
    )
}

const mapStateToProps = (state: Client) => {
    return { client: state }
}

const mapDispatchToProps = (reduxDispatch: Dispatch) => {
    return { 
        setClient: (id: string, dispatch: React.Dispatch<ChatAction>) => {
            reduxDispatch(clientSlice.actions.create(stompClient(id, dispatch)))
        }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Chatroom)
