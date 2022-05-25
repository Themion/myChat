import { Client } from "@stomp/stompjs"
import { FormEventHandler } from "react"
import { connect } from "react-redux"
import { ClientProps } from "../../app/stompStore"
import { ChatDTO, Id } from "../../routes/Chatroom"

interface Props extends ClientProps {
    id: Id
}

const ChatForm = (props: Props) => {
    const {id, client} = props

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

    return <form onSubmit={onSubmit}>
        <textarea id="chat"></textarea>
        <button type="submit">보내기</button>
    </form>
}

const mapStateToProps = (state: Client) => {
    return { client: state }
}

export default connect(mapStateToProps)(ChatForm)
