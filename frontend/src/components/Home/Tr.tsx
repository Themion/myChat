import { ChatroomDTO } from "../../types/chat"

const Tr = (props: ChatroomDTO) => {
    const onClick = () => { window.open(`/room/${props.id}`) }

    return <tr onClick={onClick}>
        <td>{props.id}</td>
        <td>{props.title}</td>
        <td>{props.population}</td>
    </tr>
}

export default Tr
