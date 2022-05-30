import { useEffect, useState } from "react"
import { Navigate, useParams } from "react-router-dom"
import Input from "../components/Chatroom/Input"
import Chats from "../components/Chatroom/ChatList"
import { AxiosDestination, AxiosCallback, AxiosFallback } from "../types/axios"
import { ChatroomDTO, Id } from "../types/chat"
import { send } from "../utils/axios"

const Chatroom = () => {
    const id: Id = Number(useParams().id)
    const [room, setRoom] = useState<ChatroomDTO>({id: Number(id), title: "", population: 0})

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
    }, [id])

    if (!id) { return <Navigate to='/' /> }

    return <>
        <h2 id='chatroom_title'>{room.title} ({room.population})</h2>
        <Chats id={id} />
        <Input id={id} />
    </>
}

export default Chatroom
