import { TodoClient } from './TodoClient.ts'
import { Todo } from './TodoModel.ts'
import axios from 'axios'

export class DefaultTodoClient implements TodoClient {
    async getTodos(): Promise<Todo[]> {
        const { data } = await axios.get('/todos')
        return Promise.resolve(data)
    }

    async deleteTodo(id: number): Promise<void> {
        await axios.delete(`/todos/${id}`)
    }
}
