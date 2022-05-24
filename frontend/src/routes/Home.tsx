import { useEffect, useState } from "react"
import Tr, { Props as ChatroomProps } from "../components/Chatroom/Tr"
import CreateChatroomForm from "../components/Chatroom/CreateChatroomForm"
import { Callback, Fallback, sendTo, send } from "../utils/axios"

const Home = () => {
    const [table, setTable] = useState(<span>loading...</span>)

    useEffect(() => {
        const to: sendTo = {
            url: "/room",
            method: "GET"
        }

        const callback: Callback = (res) => {
            const trList: JSX.Element[] = []
            const chatroomList: ChatroomProps[] = res.data

            chatroomList.forEach((tr: ChatroomProps) => {
                trList.push(<Tr key={tr.id} {...tr} />)
            })

            setTable(
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>이름</th>
                            <th>현재 인원</th>
                        </tr>
                    </thead>
                    <tbody>{trList}</tbody>
                </table>
            )
        }

        const fallback: Fallback = (res) => {
            console.log(res.data)
        }

        send(to, {}, callback, fallback)
    }, [])

    return <>
        {table}
        <CreateChatroomForm />
    </>
}


export default Home
