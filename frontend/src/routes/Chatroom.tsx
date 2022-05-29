import { Dispatch } from "@reduxjs/toolkit"
import { useEffect, useReducer, useState } from "react"
import { connect } from "react-redux"
import { Navigate, useParams } from "react-router-dom"
import { slice } from "../app/store"
import { Chat, Info } from "../components/Chatroom/Chat"
import ChatForm from "../components/Chatroom/ChatForm"
import { AxiosDestination, AxiosCallback, AxiosFallback } from "../types/axios"
import { ChatAction, ChatActionType, ChatDispatch, ChatroomDTO, Id } from "../types/chat"
import { ClientProps, State } from "../types/redux"
import { send } from "../utils/axios"
import { stompClient } from "../utils/stomp"

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
    const [room, setRoom] = useState<ChatroomDTO>({id: Number(id), title: "채팅방에 접속하려면 화면을 클릭하세요.", population: 0})
    const [chats, dispatch] = useReducer(reducer, [])

    useEffect(() => {
        const to: AxiosDestination = {
            url: `/room/${id}`,
            method: 'GET'
        }
        const callback: AxiosCallback = (res) => {
            setRoom(res.data)
        }
        const fallback: AxiosFallback = (res) => {
            window.close()
        }

        send(to, {}, callback, fallback)
    }, [id, chats])

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

const mapStateToProps = (state: State) => {
    return { client: state.client }
}

const mapDispatchToProps = (reduxDispatch: Dispatch) => {
    return { 
        setClient: (id: Id, dispatch: ChatDispatch) => {
            reduxDispatch(slice.actions.setClient(
                stompClient(id, dispatch)
            ))
        }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Chatroom)
