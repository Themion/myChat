import { Dispatch } from "@reduxjs/toolkit"
import { useEffect, useReducer, useState } from "react"
import { connect } from "react-redux"
import { slice } from "../../app/store"
import { ChatAction, ChatActionType, Id } from "../../types/chat"
import { ClientProps, State } from "../../types/redux"
import { activateClient, stompClient } from "../../utils/stomp"
import { Chat, Info, Sender } from "./Chat"

type Props = ClientProps & {
    id: Id
    getRoom: () => void
    setClient: () => void
}

const reducerFactory = (
    getRoom: Props["getRoom"], 
    lastSender: string,
    setLastSender: (lastSender: string) => any
) => {
    const reducer = (state: JSX.Element[], action: ChatAction) => {
        const { type, payload } = action
        if (payload.sender !== lastSender) {
            state.push(<Sender {...payload} />)
            setLastSender(payload.sender)
        }

        switch (type) {
            case ChatActionType.CHAT:
                return state.concat(<Chat key={payload.chatId} {...payload} />)
            case ChatActionType.INFO: 
                getRoom()
                return state.concat(<Info key={payload.chatId} {...payload} />)
            default:
                return state
        }
    }

    return reducer
}

const ChatList = (props: Props) => {
    const { id, client, setClient, getRoom } = props
    const [chats, dispatch] = useReducer(reducerFactory(getRoom, ...useState("")), [])

    useEffect(() => {
        client ? activateClient(id, client, dispatch) : setClient()
        // eslint-disable-next-line
    }, [client])

    window.onbeforeunload = () => {
        if (client) {
            client.publish({
                destination: `/ws/${id}/disconnect`
            })
            client.deactivate()
        }
    }

    return <div id="chatlist">{chats}</div>
}

const mapStateToProps = (state: State) => {
    return { client: state.client }
}

const mapDispatchToProps = (dispatch: Dispatch) => {
    return { 
        setClient: () => {
            dispatch(slice.actions.setClient(stompClient()))
        }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(ChatList)
