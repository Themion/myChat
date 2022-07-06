import { Dispatch } from "@reduxjs/toolkit"
import { Client } from "@stomp/stompjs"
import { useState, useEffect } from "react"
import { connect } from "react-redux"
import { useParams, Navigate } from "react-router-dom"
import { slice } from "../app/store"
import ChatList from "../components/Chatroom/ChatList"
import Input from "../components/Chatroom/Input"
import { AxiosDestination, AxiosCallback, AxiosFallback } from "../types/axios"
import { Id, ChatroomDTO } from "../types/chat"
import { ClientProps, State } from "../types/redux"
import { send } from "../utils/axios"
import { setTokens } from "../utils/session"
import { stompClient } from "../utils/stomp"

type Props = ClientProps & {
    setClient: (client?: Client) => void
}

const Chatroom = (props: Props) => {
    const { client, setClient } = props
    const id: Id = Number(useParams().id)
    const [room, setRoom] = useState<ChatroomDTO>({id: Number(id), title: "", population: 0})

    const getRoom = () => {
        const to: AxiosDestination = { url: `/room/${id}`, method: 'GET' }
        const callback: AxiosCallback = (res) => setRoom(res.data)
        const fallback: AxiosFallback = (res) => window.close()

        send(to, {}, callback, fallback)
    }

    const removeClient = () => {
        client?.publish({ destination: `/ws/${id}/disconnect` })
        setClient()
    }

    useEffect(() => {
        getRoom()
        setTokens().finally(() => setClient(stompClient()))

        return removeClient
        // eslint-disable-next-line
    }, [id])

    window.onbeforeunload = removeClient

    if (!id) { return <Navigate to='/' /> }

    return <>
        <h2 id='chatroom_title'>{room.title} ({room.population})</h2>
        <ChatList id={id} getRoom={getRoom} />
        <Input id={id} />
    </>
}

const mapStateToProps = (state: State) => ({ client: state.client })

const mapDispatchToProps = (dispatch: Dispatch): {
    setClient: Props["setClient"]
} => ({ setClient: (client) => dispatch(slice.actions.setClient(client)) })

export default connect(mapStateToProps, mapDispatchToProps)(Chatroom)
