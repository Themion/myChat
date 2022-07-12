import { FormEvent, FormEventHandler, KeyboardEventHandler, useState } from "react"
import { connect } from "react-redux"
import { Id, ChatDTO } from "../../types/chat"
import { ClientProps, State } from "../../types/redux"

import styles from './css/Textarea.module.css'

type Props = ClientProps & {
    id: Id
}

const Input = (props: Props) => {
    const {id, client} = props
    const [chat, setChat] = useState('')

    const onSubmit: FormEventHandler = (e) => {
        e.preventDefault()

        if (chat === "") return

        const data: Partial<ChatDTO> = { chat }
        if (client) client.publish({
            destination: `/ws/${id}`,
            body: JSON.stringify(data)
        })

        setChat('')
    }

    const onKeyDown: KeyboardEventHandler<HTMLTextAreaElement> = (e) => {
        if (e.key === "Enter") {
            if (!e.ctrlKey) {
                e.preventDefault()
                onSubmit(e)
            }
            else setChat(chat + '\n')
        }
    }

    const onChange = (e: FormEvent<HTMLTextAreaElement>) => 
        setChat(e.currentTarget.value)

    return <form className={styles.form} onSubmit={onSubmit}>
        <textarea 
            id="chat" 
            className={styles.textarea}
            value={chat}
            onChange={onChange}
            onKeyDown={onKeyDown}/>
        <button type="submit" className={styles.button}>
            <span className={styles.span}>보내기</span>
        </button>
    </form>
}

const mapStateToProps = (state: State) => ({ client: state.client })

export default connect(mapStateToProps)(Input)
