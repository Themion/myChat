import { FormEventHandler } from "react"
import { AxiosCallback, AxiosDestination, AxiosFallback } from "../../types/axios"
import { send } from "../../utils/axios"

const CreateChatroomForm = () => {
    const onSubmit: FormEventHandler = (e) => {
        e.preventDefault()

        const input = document.getElementById("title") as HTMLInputElement
        if (input.value === "") return
        
        const to: AxiosDestination = {
            url: "/room",
            method: "POST"
        }

        const data = {
            title: input.value
        }

        const callback: AxiosCallback = (res) => {
            window.open(`/room/${res.data}`)
            window.location.reload()
        }

        const fallback: AxiosFallback = (res) => {
            console.log(res)
        }

        send(to, data, callback, fallback)
    }

    return (
        <form onSubmit={onSubmit}>
            <input id="title" placeholder="새 채팅방 이름"></input>
            <button type="submit">새 채팅방 만들기</button>
        </form>
    )
}

export default CreateChatroomForm
