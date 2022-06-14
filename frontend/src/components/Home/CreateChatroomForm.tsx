import { FormEventHandler } from "react"
import { AxiosDestination, AxiosCallback, AxiosFallback } from "../../types/axios"
import { send } from "../../utils/axios"

const CreateChatroomForm = () => {
    const onSubmit: FormEventHandler = (e) => {
        e.preventDefault()

        const input = document.getElementById("title") as HTMLInputElement
        if (input.value === "") return
        
        const to: AxiosDestination = { url: "/room", method: "POST" }
        const data = { title: input.value }

        const callback: AxiosCallback = (res) => {
            window.open(`/room/${res.data}`)
            window.location.reload()
        }
        const fallback: AxiosFallback = (res) => console.log(res)

        send(to, data, callback, fallback)
    }

    return (
        <form onSubmit={onSubmit}>
            <div className="input-group mb-3">
                <input type="text" className="form-control" id="title" placeholder="새 채팅방 이름" />
                <button className="btn btn-outline-secondary" type="submit">새 채팅방 만들기</button>
            </div>
        </form>
    )
}

export default CreateChatroomForm
