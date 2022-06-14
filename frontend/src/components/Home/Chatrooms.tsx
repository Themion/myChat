import { ChatroomDTO } from "../../types/chat"
import Tr from "./Tr"

type Props = {
    chatroomDTO: ChatroomDTO[]
}

const Chatrooms = (props: Props) => {
    return <table className="table">
        <thead>
            <tr>
                <th>#</th>
                <th>이름</th>
                <th>현재 인원</th>
            </tr>
        </thead>
        <tbody>{props.chatroomDTO.map(chatroom =>
            <Tr key={chatroom.id} {...chatroom} />
        )}</tbody>
    </table>
}

export default Chatrooms
