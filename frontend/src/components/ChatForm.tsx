import { Client } from "@stomp/stompjs"
import { FormEventHandler } from "react"
import { connect } from "react-redux"

export interface ChatDTO {
    chat: string
}

interface Props {
    id: string
    client: Client
}

const ChatForm = (props: Partial<Props>) => {
    const { id, client } = props as Props

    const onSubmit: FormEventHandler = (e) => {
        e.preventDefault()
        
        const chat = document.getElementById("chat") as HTMLTextAreaElement
        if (chat.value === "") return

        const data = { chat: chat.value }
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

const mapStateToProps = (state: Client, props: Partial<Props>): Partial<Props> => {
    return { ...props, client: state }
}

export default connect(mapStateToProps)(ChatForm)
