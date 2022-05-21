import { Client, messageCallbackType } from "@stomp/stompjs";
import SockJS from "sockjs-client";

const WebSocketServer = "http://localhost:8080/websocket"

interface Subscribe {
    url: string
    callback: messageCallbackType
}

export const stompClient = (subs: Subscribe[]) => {
    const client = new Client({
        webSocketFactory: () => new SockJS(WebSocketServer)
    })
    
    client.onConnect = (frame) => {
        console.log(frame)
        subs.forEach((sub: Subscribe) => {
            client.subscribe(sub.url, sub.callback)
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
    
    client.activate()
}
