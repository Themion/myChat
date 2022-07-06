import { ChatroomDTO } from "../../types/chat"
import { openChatroom } from "../../utils/utils"

const Tr = (props: ChatroomDTO) => {
    const onClick = () => { openChatroom(props.id) }

    return <tr onClick={onClick}>
        <td>{props.id}</td>
        <td>{props.title}</td>
        <td>{props.population}</td>
    </tr>
}

export default Tr
