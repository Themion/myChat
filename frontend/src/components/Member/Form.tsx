import { Action } from "../../types/redux"
import { send } from "../../utils/axios"
import { ChangeEvent, FormEvent, useReducer } from "react"
import { AxiosCallback, AxiosDestination, AxiosFallback } from "../../types/axios"
import { InputType } from "../../types/dto"

type DTO = { [key: string]: string }

type Props = {
    inputs: InputType[],
    to: AxiosDestination,
    callback: AxiosCallback,
    fallback?: AxiosFallback
}

const reducer = (state: DTO, action: Action<string>) => {
    const {type, payload} = action
    return {...state, [type]: payload}
}

const Form = (props: Props) => {
    const { inputs, to, callback, fallback: customFallback } = props

    const initVal: DTO = inputs.reduce((obj, item) => Object.assign(obj, { [item.name]: '' }), {})

    const [data, dataDispatch] = useReducer(reducer, initVal)
    const [errorMsg, errorDispatch] = useReducer(reducer, initVal)

    const onChange = (e: ChangeEvent<HTMLInputElement>) => {
        dataDispatch({
            type: e.target.name, 
            payload: e.target.value
        })
    }

    const onSubmit = (e: FormEvent) => {
        e.preventDefault()

        console.log(data)

        const fallback: AxiosFallback = (res) => {
            const {data: {errors}} = res

            console.log(errors)
            errors.forEach((error: any) => 
                errorDispatch({
                    type: error.field, 
                    payload: error.defaultMessage
                }))
            if (customFallback) customFallback(res)
        }
        send(to, data, callback, fallback)
    }

    return <form onSubmit={onSubmit}>
        {props.inputs.map((input: InputType) => <div key={input.name}>
            <input
                name={input.name}
                type={input.type}
                placeholder={input.placeholder}
                value={data[input.name]}
                onChange={onChange} />
            <span>{errorMsg[input.name]}</span>
        </div>)}
        <button type="submit">제출</button>
    </form>
}

export default Form
