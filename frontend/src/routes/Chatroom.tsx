import { Client } from "@stomp/stompjs"
import { FormEventHandler } from "react"
import { Navigate, useParams } from "react-router-dom"
import { stompClient } from "../utils/stomp"

export interface ChatDTO {
    chat: string
}

const Chatroom = () => {
    const id = useParams().id
    let client: Client | undefined = undefined

    if (!id) return <Navigate to="/" />

    window.onclick = () => { if (!client) client = stompClient(id) }

    const onSubmit: FormEventHandler = (e) => {
        e.preventDefault()
        
        const chat = document.getElementById("chat") as HTMLTextAreaElement
        if (chat.value === "") return

        const data: ChatDTO = { chat: chat.value }
        if (client) client.publish({
            destination: `/ws/${id}`,
            body: JSON.stringify(data)
        })

        chat.value = ""
    }

    return (
        <>
            <div>id: {id}</div>
            <div id="chats">

            </div>
            <form onSubmit={onSubmit}>
                <textarea id="chat"></textarea>
                <button type="submit">보내기</button>
            </form>
        </>
    )
}

export default Chatroom
