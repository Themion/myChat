export interface Props {
    id: number
    title: string
    population: number
}

const Tr = (props: Props) => {
    const onClick = () => {
        window.open(`/${props.id}`)
    }

    return <tr onClick={onClick}>
        <td>{props.id}</td>
        <td>{props.title}</td>
        <td>{props.population}</td>
    </tr>
}

export default Tr
