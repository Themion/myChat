import { useParams } from "react-router-dom"

const Chatroom = () => {
    const id = useParams().id

    return (
        <div>id: {id}</div>
    )
}

export default Chatroom
