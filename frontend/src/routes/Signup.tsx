import { FormEventHandler } from "react"
import { useNavigate } from "react-router-dom"
import { Callback, Fallback, send, sendTo } from "../utils/axios"

interface SignupDTO {
    username: string
    password: string
    password_check: string
}

const Signup = () => {
    const navigate = useNavigate()

    const onSubmit: FormEventHandler = (e) => {
        e.preventDefault()
    
        const to: sendTo = {
            url: '/member',
            method: 'POST'
        }
    
        const callback: Callback = (res) => {
            navigate('/login')
        }
        const fallback: Fallback = (res) => {
            window.location.href = '/signup'
        }
    
        const username = document.querySelector('input#username') as HTMLInputElement
        const password = document.querySelector('input#password') as HTMLInputElement
        const password_check = document.querySelector('input#password_check') as HTMLInputElement
    
        if (password.value !== password_check.value) return
    
        const data: SignupDTO = {
            username: username.value,
            password: password.value,
            password_check: password_check.value
        }
    
        send(to, data, callback, fallback)
    }

    return <>
        <h2>회원가입</h2>
        <form onSubmit={onSubmit}>
            <div>
                <input id="username" type="text" placeholder="username"></input>
            </div>
            <div>
                <input id="password" type="password" placeholder="password"></input>
            </div>
            <div>
                <input id="password_check" type="password" placeholder="password_check"></input>
            </div>
            <button type="submit">제출</button>
        </form>
    </>
}

export default Signup
