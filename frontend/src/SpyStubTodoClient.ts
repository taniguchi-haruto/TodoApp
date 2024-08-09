import { TodoClient } from './TodoClient.ts'
import { Todo } from './TodoModel.ts'

export class SpyStubTodoClient implements TodoClient {
    getTodosReturnValue: Todo[] = []
    getTodosCalled: boolean = false

    deleteTodoCalled: boolean = false
    deleteTodoId: number = 0

    postTodoCalled: boolean = false
    postTodoText: string = ''
    postTodoReturnValue: number = 0

    getTodos(): Promise<Todo[]> {
        this.getTodosCalled = true
        return Promise.resolve(this.getTodosReturnValue)
    }

    deleteTodo(id: number): Promise<void> {
        this.deleteTodoCalled = true
        this.deleteTodoId = id
        return Promise.resolve()
    }

    postTodo(text: string): Promise<number> {
        this.postTodoCalled = true
        this.postTodoText = text
        return Promise.resolve(this.postTodoReturnValue)
    }
}
