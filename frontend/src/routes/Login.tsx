import { useNavigate } from "react-router-dom"
import Form from "../components/Member/Form"
import { AxiosDestination, AxiosCallback, AxiosFallback } from "../types/axios"
import { InputType } from "../types/dto"

const Login = () => {
    const navigate = useNavigate()

    const to: AxiosDestination = { url: '/login', method: 'POST' }
    const callback: AxiosCallback = (res) => navigate('/')
    const fallback: AxiosFallback = (res) => {
        const span = document.querySelector('span#loginError') as HTMLSpanElement

        console.log(res)
        
        span.innerHTML = res.status === 403 ? "아이디 혹은 비밀번호가 잘못되었습니다." : "알 수 없는 오류"
    }

    const inputs: InputType[] = [
        {
            name: "username",
            placeholder: "username",
            type: "text"
        }, {
            name: "password",
            placeholder: "password",
            type: "password"
        }
    ]

    return <>
        <h2>로그인</h2>
        <span id='loginError'></span>
        <Form inputs={inputs} to={to} callback={callback} fallback={fallback} />
        <button onClick={() => { navigate('/signup') }}>회원가입</button>
    </>
}

export default Login
