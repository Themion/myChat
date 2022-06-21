import { Client, IMessage } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { baseURL } from "../types/axios";
import { ChatActionType, ChatDispatch, ChatDTO, Id } from "../types/chat";
import { getAccessToken, setUsername } from "./session";

const WebSocketServer = baseURL + '/websocket'

export const stompClient = () => {
    const token = getAccessToken()

    const client = new Client({
        webSocketFactory: () => new SockJS(WebSocketServer),
        // debug: (str) => console.log(str),
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
        connectHeaders: token ? { authentication: token } : {}
    })
    
    client.onStompError = (frame) => {
        // Will be invoked in case of error encountered at Broker
        // Bad login/passcode typically will cause an error
        // Complaint brokers will set `message` header with a brief message. Body may contain details.
        // Compliant brokers will terminate the connection after any error
        console.log('Broker reported error: ' + frame.headers['message']);
        console.log('Additional details: ' + frame.body);
    }

    return client
}

const messageToDTO = (message: IMessage) => {
    const dto: ChatDTO = JSON.parse(message.body)
    dto.chatId = message.headers['message-id']

    return dto
}

export const activateClient = (id: Id, client: Client, dispatch: ChatDispatch) => {
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

        client.subscribe(`/user/queue/${id}`, (message) => {
            setUsername(message.body)
        })

        client.publish({ destination: `/ws/${id}/connect` })
    }

    client.onDisconnect = (receipt) => {
        client.unsubscribe(`/topic/${id}`)
        client.unsubscribe(`/topic/${id}/connect`)
        client.unsubscribe(`/topic/${id}/disconnect`)
        client.unsubscribe(`/user/queue/${id}`)
    }

    // client.activate()
}
