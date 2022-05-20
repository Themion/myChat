export interface Props {
    id: number
    title: string
}

const ChatroomTr = (props: Props) => {
    const onClick = () => {
        window.open(`/${props.id}`)
    }

    return <tr onClick={onClick}>
        <td>{props.id}</td>
        <td>{props.title}</td>
    </tr>
}

export default ChatroomTr
