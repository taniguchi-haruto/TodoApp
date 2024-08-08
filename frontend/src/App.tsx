import React, { useEffect, useState } from 'react'
import { TodoClient } from './TodoClient.ts'
import { Todo } from './TodoModel.ts'

interface Props {
    todoClient: TodoClient
}

const App: React.FC<Props> = ({ todoClient }) => {
    const [todos, setTodos] = useState<Todo[]>([])

    const fetchTodos = async () => {
        const fetchedTodos = await todoClient.getTodos()
        setTodos(fetchedTodos)
    }

    useEffect(() => {
        fetchTodos()
    }, [])

    return (
        <>
            <h1>Todo App</h1>

            <ul>
                {todos.map((todo) => {
                    return (
                        <li key={todo.id}>
                            <div>{todo.id}</div>
                            <div>{todo.text}</div>
                            <button
                                onClick={() => {
                                    todoClient.deleteTodo(todo.id).then(() => {
                                        const updatedTodos = [...todos].filter(
                                            (t) => t.id !== todo.id
                                        )
                                        setTodos(updatedTodos)
                                    })
                                }}
                            >
                                Delete
                            </button>
                        </li>
                    )
                })}
            </ul>
        </>
    )
}

export default App
