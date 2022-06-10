import { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import CreateChatroomForm from "../components/Home/CreateChatroomForm"
import Tr from "../components/Home/Tr"
import { AxiosDestination, AxiosCallback, AxiosFallback } from "../types/axios"
import { ChatroomDTO } from "../types/chat"
import { send } from "../utils/axios"
import { setAccessToken, getAccessToken } from "../utils/session"


const Home = () => {
    const navigate = useNavigate()
    const [table, setTable] = useState(<span>loading...</span>)

    useEffect(() => {
        const updateTable = () => {
            const to: AxiosDestination = {
                url: "/room",
                method: "GET"
            }
    
            const callback: AxiosCallback = (res) => {
                const chatroomList: ChatroomDTO[] = res.data
    
                setTable(
                    <table>
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>이름</th>
                                <th>현재 인원</th>
                            </tr>
                        </thead>
                        <tbody>{chatroomList.map(chatroom => 
                            <Tr key={chatroom.id} {...chatroom} />
                        )}</tbody>
                    </table>
                )
            }
    
            const fallback: AxiosFallback = (res) => {
                console.log(res)
            }
    
            send(to, {}, callback, fallback)
        }

        setAccessToken().finally(updateTable)
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
