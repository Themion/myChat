import { useEffect, useState } from "react"
import ChatroomTr, { Props as ChatroomProps } from "../components/ChatroomTr"
import CreateChatroomForm from "../components/CreateChatroomForm"
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

            res.data.forEach((tr: ChatroomProps) => {
                trList.push(<ChatroomTr key={tr.id} {...tr} />)
            })

            setTable(
                <table>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>이름</th>
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
