import { Client, messageCallbackType } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { ChatDTO } from "../components/ChatForm";

const WebSocketServer = "http://localhost:8080/websocket"

const chatSubscribe: messageCallbackType = (message) => {
    const dto: ChatDTO = JSON.parse(message.body)
    const chats = document.getElementById("chats") as HTMLDivElement

    const p = document.createElement("p") as HTMLParagraphElement
    p.innerText = dto.chat

    chats.appendChild(p)
}

const closeSubscribe: messageCallbackType = (message) => {
    const chats = document.getElementById("chats") as HTMLDivElement

    const p = document.createElement("p") as HTMLParagraphElement
    p.innerText = "someone has closed"

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
        client.subscribe(`/topic/${id}/disconnect`, closeSubscribe)
    };
    
    client.onStompError = (frame) => {
        // Will be invoked in case of error encountered at Broker
        // Bad login/passcode typically will cause an error
        // Complaint brokers will set `message` header with a brief message. Body may contain details.
        // Compliant brokers will terminate the connection after any error
        console.log('Broker reported error: ' + frame.headers['message']);
        console.log('Additional details: ' + frame.body);
    };
    
    client.activate()

    return client
}
