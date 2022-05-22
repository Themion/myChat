import { Client } from "@stomp/stompjs"
import { FormEventHandler } from "react"

interface Props {
    id: string
    client: Client
}

export interface ChatDTO {
    chat: string
}

export const ChatForm = (props: Props) => {
    const {id, client} = props

    const onSubmit: FormEventHandler = (e) => {
        e.preventDefault()
        
        const chat = document.getElementById("chat") as HTMLTextAreaElement

        const data: ChatDTO = { chat: chat.value }

        client.publish({
            destination: `/ws/${id}`,
            body: JSON.stringify(data)
        })

        chat.value = ""
    }
    
    return <form onSubmit={onSubmit}>
        <textarea id="chat"></textarea>
        <button type="submit">보내기</button>
    </form>
}
