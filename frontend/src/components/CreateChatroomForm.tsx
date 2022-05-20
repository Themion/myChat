import { FormEventHandler } from "react"
import { Callback, Fallback, send, sendTo } from "../utils/axios"

const CreateChatroomForm = () => {
    const onSubmit: FormEventHandler = (e) => {
        e.preventDefault()

        const input = document.getElementById("title") as HTMLInputElement
        
        const to: sendTo = {
            url: "/room",
            method: "POST"
        }

        const data = {
            title: input.value
        }

        const callback: Callback = (res) => {
            window.open(`/${res.data}`)
            window.location.reload()
        }

        const fallback: Fallback = (res) => {
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
