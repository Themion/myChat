import { useEffect, useState } from "react"
import Tr from "../components/Home/Tr"
import CreateChatroomForm from "../components/Home/CreateChatroomForm"
import { useNavigate } from "react-router-dom"
import { getAccessToken, setAccessToken } from "../utils/session"
import { send } from "../utils/axios"
import { AxiosDestination, AxiosCallback, AxiosFallback } from "../types/axios"
import { ChatroomDTO } from "../types/chat"

const Home = () => {
    const navigate = useNavigate()
    const [table, setTable] = useState(<span>loading...</span>)

    useEffect(() => {
        setAccessToken()

        const to: AxiosDestination = {
            url: "/room",
            method: "GET"
        }

        const callback: AxiosCallback = (res) => {
            const trList: JSX.Element[] = []
            const chatroomList: ChatroomDTO[] = res.data

            chatroomList.forEach((tr: ChatroomDTO) => {
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

        const fallback: AxiosFallback = (res) => {
            console.log(res.data)
        }

        send(to, {}, callback, fallback)
    }, [])

    const button = getAccessToken() ? 
        <button onClick={() => {navigate('/logout')}}>로그아웃</button> : 
        <button onClick={() => {navigate('/login')}}>로그인</button>

    return <>
        {table}
        <CreateChatroomForm />
        {button}
    </>
}


export default Home
