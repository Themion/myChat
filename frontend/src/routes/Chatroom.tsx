import { Navigate, useParams } from "react-router-dom"
import { ChatForm } from "../components/ChatForm"
import { send, sendTo } from "../utils/axios"
import { stompClient } from "../utils/stomp"

const Chatroom = () => {
    const id = useParams().id

    if (!id) return <Navigate to="/" />

    const client = stompClient(id)

    client.onDisconnect = (frame) => {
        const to: sendTo = {
            url: `/room/${id}/disconnect`,
            method: "POST"
        }
        send(to, {}, ()=>{}, ()=>{})
    }

    window.onbeforeunload = (e) => { 
        client.deactivate()
        e.returnValue = ""
    }

    return (
        <>
            <div>id: {id}</div>
            <div id="chats">

            </div>
            <ChatForm id={id} client={client} />
        </>
    )
}

export default Chatroom
