import { ChatDTO } from '../../types/chat'
import styles from './Chat.module.css'

export const Info = (props: ChatDTO) => {
    return <div className={styles.info}>
        {props.chat}
    </div>
}

export const Chat = (props: ChatDTO) => {
    return <div>
        {props.chat}
    </div>
}

export const Sender = (props: ChatDTO) => {
    return <div className={styles.sender}>
        {props.sender}
    </div>
}
