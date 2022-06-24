import { useState, useEffect, Dispatch } from "react"
import { useNavigate } from "react-router-dom"
import { getAccessToken } from "../app/store"
import Chatrooms from "../components/Home/Chatrooms"
import CreateChatroomForm from "../components/Home/CreateChatroomForm"
import { AxiosDestination, AxiosCallback, AxiosFallback } from "../types/axios"
import { send } from "../utils/axios"
import { setAccessToken } from "../utils/session"

const updateTable = (to: AxiosDestination, setTable: Dispatch<any>, text: string) => {
    const callback: AxiosCallback = (res) => setTable(<>
        {text}
        <Chatrooms chatroomDTO={res.data} />
    </>)
    const fallback: AxiosFallback = (res) => console.log(res)

    send(to, {}, callback, fallback)
}

const Home = () => {
    const navigate = useNavigate()
    const [visited, setVisited] = useState(<></>)
    const [table, setTable] = useState(<span>loading...</span>)

    useEffect(() => {
        setAccessToken()
            .then(() => {
                const to: AxiosDestination = { url: "/member", method: "GET" }
                updateTable(to, setVisited, "사용자가 들어간 적 있는 채팅방:") 
            })
            .finally(() => { 
                const to: AxiosDestination = { url: "/room", method: "GET" }
                updateTable(to, setTable, "현재 열려있는 채팅방:") 
            })
    }, [])

    const button = getAccessToken() ? 
        <button onClick={() => {navigate('/logout')}}>로그아웃</button> : 
        <button onClick={() => {navigate('/login')}}>로그인</button>

    return <>
        {visited}
        {table}
        <CreateChatroomForm />
        {button}
    </>
}

export default Home
