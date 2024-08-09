import { TodoClient } from './TodoClient.ts'
import { vi } from 'vitest'

export class MockTodoClient implements TodoClient {
    getTodos = vi.fn().mockResolvedValue([])

    deleteTodo = vi.fn().mockResolvedValue({})

    postTodo = vi.fn().mockResolvedValue(-1)
}
