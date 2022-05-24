import { ChatDTO } from '../../routes/Chatroom'
import styles from './Chat.module.css'

export const Info = (props: ChatDTO) => {
    return <div className={styles.info}>
        {props.chat}
    </div>
}

export const Chat = (props: ChatDTO) => {
    const sender = props.sender ? <>{props.sender}<br /></> : <></>
    return <div className={styles.chat}>
        {sender}
        {props.chat}
    </div>
}
