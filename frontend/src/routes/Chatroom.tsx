import {messageCallbackType } from "@stomp/stompjs"
import { FormEventHandler } from "react"
import { useParams } from "react-router-dom"
import { Callback, Fallback, send, sendTo } from "../utils/axios"
import { stompClient } from "../utils/stomp"


interface ChatDTO {
    chat: string
}

const Chatroom = () => {
    const id = useParams().id

    const url = `/topic/${id}`
    const callback: messageCallbackType = (message) => {
        const dto: ChatDTO = JSON.parse(message.body)
        const chats = document.getElementById("chats") as HTMLDivElement

        const p = document.createElement("p") as HTMLParagraphElement
        p.innerText = dto.chat

        chats.appendChild(p)
    }

    stompClient([{url, callback}])

    const onSubmit: FormEventHandler = (e) => {
        e.preventDefault()
        
        const chat = document.getElementById("chat") as HTMLTextAreaElement

        const to: sendTo = {
            url: `/room/${id}`,
            method: "POST"
        }

        const data: ChatDTO = { chat: chat.value }

        const callback: Callback = (res) => {}
        const fallback: Fallback = (res) => { console.log(res) }

        send(to, data, callback, fallback)

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
