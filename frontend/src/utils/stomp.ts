import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { getTokenPayload } from "./session";

const WebSocketServer = "http://localhost:8080/websocket"

export const stompClient = () => {
    const payload = getTokenPayload()

    const client = new Client({
        webSocketFactory: () => new SockJS(WebSocketServer),
        // debug: (str) => console.log(str),
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
        connectHeaders: payload ? { username: payload.sub } : {}
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
