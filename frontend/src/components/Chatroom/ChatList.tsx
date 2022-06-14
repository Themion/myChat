import { Dispatch } from "@reduxjs/toolkit"
import { Client } from "@stomp/stompjs"
import { useReducer, useState, useEffect } from "react"
import { connect } from "react-redux"
import { slice } from "../../app/store"
import { Id, ChatAction, ChatActionType } from "../../types/chat"
import { ClientProps, State } from "../../types/redux"
import { setAccessToken } from "../../utils/session"
import { activateClient, stompClient } from "../../utils/stomp"
import { Sender, Chat, Info } from "./Chat"

type Props = ClientProps & {
    id: Id
    getRoom: () => void
    setClient: (client?: Client) => void
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
    const { id, client, setClient, getRoom } = props
    const [chats, dispatch] = useReducer(
        reducer(getRoom, ...useState("")), []
    )

    const closeClient = () => {
        if (client) {
            client.publish({ destination: `/ws/${id}/disconnect` })
            setClient()
        }
    }

    useEffect(() => {
        client ? 
            activateClient(id, client, dispatch) :
            setAccessToken().finally(() => setClient(stompClient()))

        return closeClient
        // eslint-disable-next-line
    }, [client])

    window.onbeforeunload = closeClient

    return <div id="chatlist">{chats}</div>
}

const mapStateToProps = (state: State) => ({ client: state.client })

const mapDispatchToProps = (dispatch: Dispatch): {
    setClient: Props["setClient"]
} => ({ setClient: (client) => dispatch(slice.actions.setClient(client)) })

export default connect(mapStateToProps, mapDispatchToProps)(ChatList)
