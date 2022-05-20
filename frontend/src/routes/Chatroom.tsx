import { Stomp } from "@stomp/stompjs"
import { useParams } from "react-router-dom"
import SockJS from "sockjs-client"

const Chatroom = () => {
    const id = useParams().id
    const sock = new SockJS('http://localhost:8080/websocket')
    const client = Stomp.over(sock)

    client.connect({}, (res: any) => {
        console.log(res)
    })

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
