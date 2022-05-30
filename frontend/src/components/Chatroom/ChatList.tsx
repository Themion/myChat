import { Dispatch } from "@reduxjs/toolkit"
import { useReducer } from "react"
import { connect } from "react-redux"
import { slice } from "../../app/store"
import { ChatAction, ChatActionType, ChatDTO, Id } from "../../types/chat"
import { ClientProps, State } from "../../types/redux"
import { stompClient } from "../../utils/stomp"
import { Chat, Info } from "./Chat"

interface Props extends ClientProps {
    id: Id
    setClient: () => void
}

const reducer = (state: JSX.Element[], action: ChatAction) => {
    const { type, payload } = action
    switch (type) {
        case ChatActionType.CHAT:
            return state.concat(<Chat {...payload} />)
        case ChatActionType.INFO: 
            return state.concat(<Info {...payload} />)
        default:
            return state
    }
}

const ChatList = (props: Props) => {
    const [chats, dispatch] = useReducer(reducer, [])
    const { id, client, setClient } = props
    
    if (!client) setClient()
    else {
        client.onConnect = (frame) => {
            client.subscribe(`/topic/${id}`, (message) => {
                const dto: ChatDTO = JSON.parse(message.body)
                dispatch({type: ChatActionType.CHAT, payload: dto})
            })
            client.subscribe(`/topic/${id}/connect`, (message) => {
                const dto: ChatDTO = JSON.parse(message.body)
                dispatch({type: ChatActionType.INFO, payload: dto})
            })
            client.subscribe(`/topic/${id}/disconnect`, (message) => {
                const dto: ChatDTO = JSON.parse(message.body)
                dispatch({type: ChatActionType.INFO, payload: dto})
            })
    
            client.publish({
                destination: `/ws/${id}/connect`
            })
        }
    
        window.onbeforeunload = (e) => { 
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
