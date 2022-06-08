import { Navigate } from "react-router-dom"
import { removeAccessToken } from "../utils/session"

const Logout = () => {
    removeAccessToken()
    return <Navigate to='/' />
}

export default Logout
