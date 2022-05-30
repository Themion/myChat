import { FormEventHandler } from "react"
import { connect } from "react-redux"
import { Id, ChatDTO } from "../../types/chat"
import { ClientProps, State } from "../../types/redux"

interface Props extends ClientProps {
    id: Id
}

const Input = (props: Props) => {
    const {id, client} = props

    const onSubmit: FormEventHandler = (e) => {
        e.preventDefault()
        
        const chat = document.getElementById("chat") as HTMLTextAreaElement
        if (chat.value === "") return

        const data: Partial<ChatDTO> = { chat: chat.value }
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

const mapStateToProps = (state: State) => {
    return { client: state.client }
}

export default connect(mapStateToProps)(Input)