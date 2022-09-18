import { useNavigate } from "react-router-dom"
import Form from "../components/Member/Form"
import { AxiosDestination, AxiosCallback } from "../types/axios"
import { InputType } from "../types/dto"

const Signup = () => {
    const navigate = useNavigate()
    
    const to: AxiosDestination = {
        url: '/member',
        method: 'POST'
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
        }, {
            name: "password_check",
            placeholder: "password check",
            type: "password"
        }
    ]

    const callback: AxiosCallback = (res) => navigate('/login')

    return <>
        <h2>회원가입</h2>
        <Form inputs={inputs} to={to} callback={callback} />
        <button onClick={() => { navigate('/login') }}>로그인</button>
    </>
}

export default Signup
