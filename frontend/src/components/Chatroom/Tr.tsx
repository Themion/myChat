import { Id } from "../../routes/Chatroom"

export interface Props {
    id: Id
    title: string
    population: number
}

const Tr = (props: Props) => {
    const onClick = () => {
        window.open(`/room/${props.id}`)
    }

    return <tr onClick={onClick}>
        <td>{props.id}</td>
        <td>{props.title}</td>
        <td>{props.population}</td>
    </tr>
}

export default Tr
