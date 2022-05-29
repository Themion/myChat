import React from "react"

export type Id = number

export interface ChatDTO {
    chat: string
    sender?: string
}

export interface ChatroomDTO {
    id: Id
    title: string
    population: number
}

export interface ChatAction {
    type: string
    payload: ChatDTO
}

export type ChatDispatch = React.Dispatch<ChatAction>

export enum ChatActionType {
    CHAT = "chat", INFO = "info"
}
