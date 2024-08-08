import { Todo } from './TodoModel.ts'

export interface TodoClient {
    getTodos(): Promise<Todo[]>

    deleteTodo(id: number): Promise<void>
}
