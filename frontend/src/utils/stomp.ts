import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { ChatActionType, ChatDispatch, ChatDTO, Id } from "../routes/Chatroom";

const WebSocketServer = "http://localhost:8080/websocket"

export const stompClient = (id: Id, dispatch: ChatDispatch) => {
    const client = new Client({
        webSocketFactory: () => new SockJS(WebSocketServer),
        debug: (str) => console.log(str),
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
    })
    
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
    };
    
    client.onStompError = (frame) => {
        // Will be invoked in case of error encountered at Broker
        // Bad login/passcode typically will cause an error
        // Complaint brokers will set `message` header with a brief message. Body may contain details.
        // Compliant brokers will terminate the connection after any error
        console.log('Broker reported error: ' + frame.headers['message']);
        console.log('Additional details: ' + frame.body);
    };

    window.onbeforeunload = (e) => { 
        client.publish({
            destination: `/ws/${id}/disconnect`
        })
        client.deactivate()
        e.returnValue = ""
    }
    
    client.activate()

    return client
}
