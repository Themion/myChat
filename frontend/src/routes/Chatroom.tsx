import { Dispatch } from "@reduxjs/toolkit"
import { Client } from "@stomp/stompjs"
import { connect } from "react-redux"
import { Navigate, useParams } from "react-router-dom"
import { clientSlice } from "../app/clientStore"
import ChatForm from "../components/ChatForm"

interface Props {
    client: Client
    setClient: (id: string) => void
}

const Chatroom = (props: Props) => {
    const id = useParams().id
    const { setClient } = props

    if (!id) return <Navigate to="/" />

    window.onclick = () => { if (!props.client) setClient(id) }

    // onbeforeunload: window와 상호작용한 후에야 제대로 동작
    // client를 window.onclick에서 redux에 등록해서 사용할 것
    // https://developer.mozilla.org/en-US/docs/web/api/window/beforeunload_event

    return (
        <>
            <div>id: {id}</div>
            <div id="chats">

            </div>
            <ChatForm id={id} />
        </>
    )
}

const mapStateToProps = (state: Client) => {
    return { client: state }
}

const mapDispatchToProps = (dispatch: Dispatch) => {
    return { 
        setClient: (id: string) => {
            dispatch(clientSlice.actions.create(id))
        }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Chatroom)
