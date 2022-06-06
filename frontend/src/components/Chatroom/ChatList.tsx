import { Dispatch } from "@reduxjs/toolkit"
import { IMessage } from "@stomp/stompjs"
import { useEffect, useReducer } from "react"
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

const messageToDTO = (message: IMessage) => {
    const dto: ChatDTO = JSON.parse(message.body)
    dto.chatId = message.headers['message-id']

    return dto
}

const reducer = (state: JSX.Element[], action: ChatAction) => {
    const { type, payload } = action
    switch (type) {
        case ChatActionType.CHAT:
            return state.concat(<Chat key={payload.chatId} {...payload} />)
        case ChatActionType.INFO: 
            return state.concat(<Info key={payload.chatId} {...payload} />)
        default:
            return state
    }
}

const ChatList = (props: Props) => {
    const [chats, dispatch] = useReducer(reducer, [])
    const { id, client, setClient } = props

    useEffect(() => {
        console.log(client)
        if (!client) setClient()
        else {
            client.onConnect = (frame) => {
                client.subscribe(`/topic/${id}`, (message) => {
                    dispatch({
                        type: ChatActionType.CHAT, 
                        payload: messageToDTO(message)
                    })
                })
                client.subscribe(`/topic/${id}/connect`, (message) => {
                    dispatch({
                        type: ChatActionType.INFO, 
                        payload: messageToDTO(message)
                    })
                })
                client.subscribe(`/topic/${id}/disconnect`, (message) => {
                    dispatch({
                        type: ChatActionType.INFO, 
                        payload: messageToDTO(message)
                    })
                })
        
                client.publish({
                    destination: `/ws/${id}/connect`
                })
            }
        }
    }, [id, client, setClient])

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
