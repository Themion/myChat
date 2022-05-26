import { Dispatch } from "@reduxjs/toolkit"
import { Client } from "@stomp/stompjs"
import React, { useEffect, useReducer, useState } from "react"
import { connect } from "react-redux"
import { Navigate, useNavigate, useParams } from "react-router-dom"
import { ClientProps, clientSlice } from "../app/stompStore"
import { Chat, Info } from "../components/Chatroom/Chat"
import ChatForm from "../components/Chatroom/ChatForm"
import {Props as ChatroomDTO} from "../components/Chatroom/Tr"
import { Callback, Fallback, send, sendTo } from "../utils/axios"
import { stompClient } from "../utils/stomp"

export interface ChatDTO {
    chat: string
    sender?: string
}

interface ChatAction {
    type: string
    payload: ChatDTO
}

export type Id = number

export type ChatDispatch = React.Dispatch<ChatAction>

export enum ChatActionType {
    CHAT = "chat", INFO = "info"
}

interface Props extends ClientProps {
    setClient: (id: Id, dispatch: ChatDispatch) => void
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
    const id: Id = Number(useParams().id)
    const [room, setRoom] = useState<ChatroomDTO>({id: Number(id), title: "", population: 0})
    const [chats, dispatch] = useReducer(reducer, [])
    const navigate = useNavigate()

    useEffect(() => {
        const to: sendTo = {
            url: `/room/${id}`,
            method: 'GET'
        }
        const callback: Callback = (res) => {
            setRoom(res.data)
        }
        const fallback: Fallback = (res) => {
            window.close()
        }

        send(to, {}, callback, fallback)
    }, [id, navigate, chats])

    if (!id) { return <Navigate to='/' /> }

    window.onclick = () => { 
        if (!props.client) props.setClient(id, dispatch) 
    }

    return <>
        <h2 id='chatroom_title'>{room.title} ({room.population})</h2>
        <div id="chats">{chats}</div>
        <ChatForm id={id} />
    </>
}

const mapStateToProps = (state: Client) => {
    return { client: state }
}

const mapDispatchToProps = (reduxDispatch: Dispatch) => {
    return { 
        setClient: (id: Id, dispatch: ChatDispatch) => {
            reduxDispatch(clientSlice.actions.create(
                stompClient(id, dispatch)
            ))
        }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Chatroom)
