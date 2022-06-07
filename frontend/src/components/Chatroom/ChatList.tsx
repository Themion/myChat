import { Dispatch } from "@reduxjs/toolkit"
import { useEffect, useReducer } from "react"
import { connect } from "react-redux"
import { slice } from "../../app/store"
import { ChatAction, ChatActionType, Id } from "../../types/chat"
import { ClientProps, State } from "../../types/redux"
import { activateClient, stompClient } from "../../utils/stomp"
import { Chat, Info } from "./Chat"

type Props = ClientProps & {
    id: Id
    getRoom: Function
    setClient: () => void
}

const reducerFactory = (getRoom: Function) => {
    const reducer = (state: JSX.Element[], action: ChatAction) => {
        const { type, payload } = action
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
    const [chats, dispatch] = useReducer(reducerFactory(props.getRoom), [])
    const { id, client, setClient } = props


    useEffect(() => {
        if (!client) setClient()
        else activateClient(id, client, dispatch)
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
