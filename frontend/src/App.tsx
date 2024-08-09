import React, { useCallback, useEffect, useState } from 'react'
import { TodoClient } from './TodoClient.ts'
import { Todo } from './TodoModel.ts'

interface Props {
    todoClient: TodoClient
}

const App: React.FC<Props> = ({ todoClient }) => {
    const [todos, setTodos] = useState<Todo[]>([])
    const [newTodoText, setNewTodoText] = useState<string>('')
    const fetchTodos = useCallback(async () => {
        const fetchedTodos = await todoClient.getTodos()
        setTodos(fetchedTodos)
    }, [todoClient])

    useEffect(() => {
        fetchTodos()
    }, [fetchTodos])

    const onClickHandler = () => {}

    return (
        <>
            <button onClick={onClickHandler}>Click me</button>
            <button
                onClick={() => {
                    onClickHandler()
                }}
            >
                Click me
            </button>
            <h1>Todo App</h1>
            <input
                placeholder="Enter task to finish"
                value={newTodoText}
                onChange={(word) => setNewTodoText(word.target.value)}
            />
            <button
                disabled={newTodoText.trim() === ''}
                onClick={async () => {
                    const newId = await todoClient.postTodo(newTodoText)
                    // option 1
                    // const copiedTodos = [...todos]
                    // copiedTodos.push({
                    //     id: newId,
                    //     text: newTodoText,
                    // })
                    // setTodos(copiedTodos)
                    setTodos([...todos, { id: newId, text: newTodoText }])
                    setNewTodoText('')
                }}
            >
                Post
            </button>
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
