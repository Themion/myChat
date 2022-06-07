import React from "react"

export type Id = number

export type ChatDTO = {
    chat: string
    sender: string
    chatId: string
}

export type ChatroomDTO = {
    id: Id
    title: string
    population: number
}

export type ChatAction = {
    type: string
    payload: ChatDTO
}

export type ChatDispatch = React.Dispatch<ChatAction>

export enum ChatActionType {
    CHAT = "chat", INFO = "info"
}
