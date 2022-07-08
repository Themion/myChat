import { ChatDTO } from '../../types/chat'
import { getUsername } from '../../utils/session'
import styles from './css/Chat.module.css'

export const Info = (props: ChatDTO) => {
    return <div className={styles.info}>
        {props.chat}
    </div>
}

export const Chat = (props: ChatDTO) => {
    const { chat, sender } = props

    const isMine = (getUsername() === sender) ? styles.myChat : ''
    return <div className={isMine}>
        {chat}
    </div>
}

export const Sender = (props: ChatDTO) => {
    const { sender } = props

    const isMine = (getUsername() === sender) ? styles.myChat : ''
    return <div className={`${styles.sender} ${isMine}`}>
        <h5 className={styles.header}>{sender}</h5>
    </div>
}
