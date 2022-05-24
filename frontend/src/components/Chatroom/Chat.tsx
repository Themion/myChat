import { ChatDTO } from '../../routes/Chatroom'
import styles from './Chat.module.css'

export const Info = (props: ChatDTO) => {
    return <div className={styles.center}>
        {props.chat}
    </div>
}

export const Chat = (props: ChatDTO) => {
    const sender = props.sender ? <>{props.sender}<br /></> : <></>
    return <div>
        {sender}
        {props.chat}
    </div>
}
