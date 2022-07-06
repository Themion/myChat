import { FormEventHandler } from "react"
import { useNavigate } from "react-router-dom"
import { AxiosDestination, AxiosCallback, AxiosFallback } from "../types/axios"
import { send } from "../utils/axios"

interface LoginDTO {
    username: string
    password: string
}

const Login = () => {
    const navigate = useNavigate()

    const onSubmit: FormEventHandler = (e) => {
        e.preventDefault()

        const username = document.querySelector('input#username') as HTMLInputElement
        const password = document.querySelector('input#password') as HTMLInputElement

        const to: AxiosDestination = { url: '/login', method: 'POST' }
        const callback: AxiosCallback = (res) => navigate('/')
        const fallback: AxiosFallback = (res) => {
            const span = document.querySelector('span') as HTMLSpanElement

            username.value = ""
            password.value = ""
            console.log(res)

            span.innerHTML = res.status === 403 ? "아이디 혹은 비밀번호가 잘못되었습니다." : "알 수 없는 오류"
        }

        const data: LoginDTO = {
            username: username.value,
            password: password.value
        }

        send(to, data, callback, fallback)
    }

    return <>
        <h2>로그인</h2>
        <form onSubmit={onSubmit}>
            <span></span>
            <div>
                <input id="username" type="text" placeholder="username"></input>
            </div>
            <div>
                <input id="password" type="password" placeholder="password"></input>
            </div>
            <button type="submit">제출</button>
        </form>
        <button onClick={() => { navigate('/signup') }}>회원가입</button>
    </>
}

export default Login
