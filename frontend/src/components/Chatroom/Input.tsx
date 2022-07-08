import { FormEventHandler, KeyboardEventHandler } from "react"
import { connect } from "react-redux"
import { Id, ChatDTO } from "../../types/chat"
import { ClientProps, State } from "../../types/redux"

import styles from './css/input.module.css'

type Props = ClientProps & {
    id: Id
}

const Input = (props: Props) => {
    const {id, client} = props

    const onSubmit: FormEventHandler = (e) => {
        e.preventDefault()
        
        const chat = document.getElementById("chat") as HTMLTextAreaElement
        if (chat.value === "") return

        const data: Partial<ChatDTO> = { chat: chat.value }
        if (client) client.publish({
            destination: `/ws/${id}`,
            body: JSON.stringify(data)
        })

        chat.value = ""
    }

    const keydown: KeyboardEventHandler<HTMLTextAreaElement> = (e) => {
        if (e.key === "Enter") {
            if (!e.ctrlKey) {
                e.preventDefault()
                onSubmit(e)
            }
            else {
                const target = e.target as HTMLTextAreaElement
                target.value += '\n'
            }
        }
    }

    return <form className={styles.form} onSubmit={onSubmit}>
        <textarea 
            id="chat" 
            className={styles.textarea}
            onKeyDown={keydown}/>
        <button type="submit" className={styles.button}>
            <span className={styles.span}>보내기</span>
        </button>
    </form>
}

const mapStateToProps = (state: State) => ({ client: state.client })

export default connect(mapStateToProps)(Input)
