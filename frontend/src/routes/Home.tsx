import { useEffect, useState } from "react"
import Chatroom, { Props as ChatroomProps } from "../components/Chatroom"
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
                trList.push(<Chatroom key={tr.id} {...tr} />)
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

    return table
}


export default Home
