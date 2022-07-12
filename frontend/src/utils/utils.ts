import { Id } from "../types/chat"

export const openChatroom = (id: Id) => {
    const option = "width = 300, height = 400, location = no, status = no"
    window.open(`/room/${id}`, undefined, option)
    window.location.reload()
}
