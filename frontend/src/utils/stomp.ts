import { Client, messageCallbackType } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { ChatDTO } from "../routes/Chatroom";
// import { send, sendTo } from "./axios";

const WebSocketServer = "http://localhost:8080/websocket"

export interface PublishType {
    id: string
    chat: string
}

const chatSubscribe: messageCallbackType = (message) => {
    const dto: ChatDTO = JSON.parse(message.body)
    addChat(dto.chat)
}

const connectSubscribe: messageCallbackType = () => {
    addChat("someone has connected")
}

const disconnectSubscribe: messageCallbackType = () => {
    addChat("someone has disconnected")
}

const addChat = (chat: string) => {
    const chats = document.getElementById("chats") as HTMLDivElement

    const p = document.createElement("p") as HTMLParagraphElement
    p.innerText = chat

    chats.appendChild(p)
}

export const stompClient = (id: string) => {
    const client = new Client({
        webSocketFactory: () => new SockJS(WebSocketServer),
        debug: (str) => console.log(str),
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
    })
    
    client.onConnect = (frame) => {
        client.subscribe(`/topic/${id}`, chatSubscribe)
        client.subscribe(`/topic/${id}/connect`, connectSubscribe)
        client.subscribe(`/topic/${id}/disconnect`, disconnectSubscribe)

        client.publish({
            destination: `/ws/${id}/connect`
        })
    };

    // client.onDisconnect = (frame) => {
    //     const to: sendTo = {
    //         url: `/room/${id}/disconnect`,
    //         method: "POST"
    //     }
    //     send(to, {}, ()=>{}, ()=>{})
    // }
    
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
