import { useReducer, useState, useEffect } from "react"
import { connect } from "react-redux"
import { Id, ChatAction, ChatActionType } from "../../types/chat"
import { ClientProps, State } from "../../types/redux"
import { activateClient } from "../../utils/stomp"
import { Sender, Chat, Info } from "./Chat"

import styles from "./ChatList.module.css"

type Props = ClientProps & {
    id: Id
    getRoom: () => void
}

const reducer = (
    getRoom: Props["getRoom"], 
    lastSender: string,
    setLastSender: React.Dispatch<string>
) => {
    return (state: JSX.Element[], action: ChatAction) => {
        const { type, payload } = action
        const { sender, chatId } = payload
    
        if (sender !== lastSender) {
            state.push(<Sender key={sender} {...payload} />)
            setLastSender(sender)
        }

        switch (type) {
            case ChatActionType.CHAT:
                return state.concat(<Chat key={chatId} {...payload} />)
            case ChatActionType.INFO: 
                getRoom()
                return state.concat(<Info key={chatId} {...payload} />)
        }
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

    return <div className={styles.chatlist}>{chats}</div>
}

const mapStateToProps = (state: State) => ({ client: state.client })

export default connect(mapStateToProps)(ChatList)
