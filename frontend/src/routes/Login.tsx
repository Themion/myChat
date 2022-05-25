import { FormEventHandler } from "react"
import { useNavigate } from "react-router-dom"
import { Callback, Fallback, send, sendTo } from "../utils/axios"

interface LoginDTO {
    username: string
    password: string
}

const Login = () => {
    const navigate = useNavigate()

    const onSubmit: FormEventHandler = (e) => {
        e.preventDefault()

        const to: sendTo = {
            url: '/login',
            method: 'POST'
        }

        const callback: Callback = (res) => {
            navigate('/')
        }
        const fallback: Fallback = (res) => {
            window.location.href = '/login?error'
        }

        const username = document.querySelector('input#username') as HTMLInputElement
        const password = document.querySelector('input#password') as HTMLInputElement

        const data: LoginDTO = {
            username: username.value,
            password: password.value
        }

        send(to, data, callback, fallback)
    }

    return <>
        <h2>로그인</h2>
        <form onSubmit={onSubmit}>
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
