import { Client } from "@stomp/stompjs"
import { useParams } from "react-router-dom"
import SockJS from "sockjs-client"

const WebSocketServer = "http://localhost:8080/websocket"

const Chatroom = () => {
    const id = useParams().id

    const client = new Client({
        webSocketFactory: () => new SockJS(WebSocketServer)
    })

    client.onConnect = (frame) => {
        // Do something, all subscribes must be done is this callback
        // This is needed because this will be executed after a (re)connect
        console.log(frame)
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

    return (
        <>
            <div>id: {id}</div>
            <div id="chats">

            </div>
            <form>
                <textarea></textarea>
                <button type="submit">채팅 송신</button>
            </form>
        </>
    )
}

export default Chatroom
