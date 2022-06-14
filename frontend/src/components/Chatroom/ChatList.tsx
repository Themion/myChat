import { useReducer, useState, useEffect } from "react"
import { connect } from "react-redux"
import { Id, ChatAction, ChatActionType } from "../../types/chat"
import { ClientProps, State } from "../../types/redux"
import { activateClient } from "../../utils/stomp"
import { Sender, Chat, Info } from "./Chat"

type Props = ClientProps & {
    id: Id
    getRoom: () => void
}

const reducer = (
    getRoom: Props["getRoom"], 
    lastSender: string,
    setLastSender: (lastSender: string) => any
) => {
    return (state: JSX.Element[], action: ChatAction) => {
        const { type, payload } = action
        if (payload.sender !== lastSender) {
            state.push(<Sender key={payload.sender} {...payload} />)
            setLastSender(payload.sender)
        }

        switch (type) {
            case ChatActionType.CHAT:
                return state.concat(<Chat key={payload.chatId} {...payload} />)
            case ChatActionType.INFO: 
                getRoom()
                return state.concat(<Info key={payload.chatId} {...payload} />)
        }

        return state
    }
}

const ChatList = (props: Props) => {
    const { id, client, getRoom } = props
    const [chats, dispatch] = useReducer(
        reducer(getRoom, ...useState("")), []
    )

    useEffect(() => {
        if (client) activateClient(id, client, dispatch)
        // eslint-disable-next-line
    }, [client])

    return <div id="chatlist">{chats}</div>
}

const mapStateToProps = (state: State) => ({ client: state.client })

export default connect(mapStateToProps)(ChatList)
