import { FormEventHandler } from "react"
import { useNavigate } from "react-router-dom"
import { AxiosDestination, AxiosCallback, AxiosFallback } from "../types/axios"
import { send } from "../utils/axios"

interface SignupDTO {
    username: string
    password: string
    password_check: string
}

const Signup = () => {
    const navigate = useNavigate()

    const onSubmit: FormEventHandler = (e) => {
        e.preventDefault()
    
        const to: AxiosDestination = {
            url: '/member',
            method: 'POST'
        }
    
        const callback: AxiosCallback = (res) => {
            navigate('/login')
        }
        const fallback: AxiosFallback = (res) => {
            console.log(res.data.errors)
            res.data.errors.forEach((error: any) => {
                const input = document.querySelector(`input#${error.field}`) as HTMLInputElement
                const span = input.parentElement?.querySelector('span') as HTMLSpanElement

                span.innerHTML = error.defaultMessage
            })
            // window.location.href = '/signup'
        }
    
        const username = document.querySelector('input#username') as HTMLInputElement
        const password = document.querySelector('input#password') as HTMLInputElement
        const password_check = document.querySelector('input#password_check') as HTMLInputElement
    
        if (password.value !== password_check.value) {
            password.value = ""
            password_check.value = ""
            return
        }
    
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
                <span></span>
            </div>
            <div>
                <input id="password" type="password" placeholder="password"></input>
                <span></span>
            </div>
            <div>
                <input id="password_check" type="password" placeholder="password_check"></input>
                <span></span>
            </div>
            <button type="submit">제출</button>
        </form>
        <button onClick={() => {navigate('/login')}}>로그인</button>
    </>
}

export default Signup
