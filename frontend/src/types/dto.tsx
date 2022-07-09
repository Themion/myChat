export type SignupDTO = {
    username: string
    password: string
    passwordCheck: string
}

export type LoginDTO = {
    username: string
    password: string
}

export type InputType = {
    name: string
    type: "text" | "password"
    placeholder: string
}
