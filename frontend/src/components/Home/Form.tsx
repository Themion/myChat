import { FormEventHandler } from "react"
import { AxiosDestination, AxiosCallback, AxiosFallback } from "../../types/axios"
import { send } from "../../utils/axios"
import { openChatroom } from "../../utils/utils"

const Form = () => {
    const onSubmit: FormEventHandler = (e) => {
        e.preventDefault()

        const input = document.getElementById("title") as HTMLInputElement
        if (input.value === "") return
        
        const to: AxiosDestination = { url: "/room", method: "POST" }
        const data = { title: input.value }

        const callback: AxiosCallback = (res) => {
            openChatroom(res.data)
            window.location.reload()
        }
        const fallback: AxiosFallback = (res) => console.log(res)

        send(to, data, callback, fallback)
    }

    return (
        <form onSubmit={onSubmit}><>
            <input
                type="text" 
                id="title" 
                placeholder="새 채팅방 이름" />
            <button type="submit">새 채팅방 만들기</button>
        </></form>
    )
}

export default Form

