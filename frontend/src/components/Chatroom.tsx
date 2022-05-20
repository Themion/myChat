export interface Props {
    id: number
    title: string
}

const Chatroom = (props: Props) => {
    return <tr>
        <td>{props.id}</td>
        <td>{props.title}</td>
    </tr>
}

export default Chatroom
