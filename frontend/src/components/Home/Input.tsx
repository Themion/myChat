import { FormEvent, FormEventHandler, useState } from "react"
import { AxiosDestination, AxiosCallback, AxiosFallback } from "../../types/axios"
import { send } from "../../utils/axios"
import { openChatroom } from "../../utils/utils"

import styles from "./css/Input.module.css"

const Form = () => {
    const [title, setTitle] = useState('')

    const onChange = (e: FormEvent<HTMLInputElement>) =>
        setTitle(e.currentTarget.value)
    
    const onSubmit: FormEventHandler = (e) => {
        e.preventDefault()
        if (title === "") return
        
        const to: AxiosDestination = { url: "/room", method: "POST" }
        const callback: AxiosCallback = (res) => openChatroom(res.data)
        const fallback: AxiosFallback = (res) => console.log(res)

        send(to, { title }, callback, fallback)
    }

    return (
        <form className={styles.form} onSubmit={onSubmit}>
            <input 
                className={styles.input}
                type="text" 
                id="title" 
                placeholder="새 채팅방 이름"
                value={title}
                onChange={onChange} />
            <button 
                className={styles.button}
                type="submit"><span className={styles.span}>새 채팅방 만들기</span></button>
        </form>
    )
}

export default Form

