import { describe, it, vi, expect } from 'vitest'
import axios, { AxiosResponse } from 'axios'
import { DefaultTodoClient } from './DefaultTodoClient.ts'

describe('DefaultTodoClient Tests', () => {
    describe('getTodos Tests', () => {
        it('should call /todos with GET', () => {
            const spy = vi.spyOn(axios, 'get').mockResolvedValue({
                status: 200,
                data: [],
            })

            const client = new DefaultTodoClient()
            client.getTodos()

            expect(spy).toHaveBeenCalledWith('/todos')
        })

        it('should return list of todo', async () => {
            vi.spyOn(axios, 'get').mockResolvedValue({
                status: 200,
                data: [
                    {
                        id: 1,
                        text: 'Learn React',
                    },
                ],
            } as AxiosResponse)

            const client = new DefaultTodoClient()
            const actual = await client.getTodos()

            expect(actual.length).toBe(1)
            expect(actual[0].id).toBe(1)
            expect(actual[0].text).toBe('Learn React')
        })
    })

    describe('deleteTodo Tests', () => {
        it('should call /todos/{id} with DELETE', () => {
            const spy = vi.spyOn(axios, 'delete').mockResolvedValue({
                status: 200,
                data: {},
            })

            const client = new DefaultTodoClient()
            client.deleteTodo(1)

            expect(spy).toHaveBeenCalledWith('/todos/1')
        })
    })

    describe('postTodo Tests', () => {
        it('should call /todos with POST', async () => {
            const spy = vi.spyOn(axios, 'post').mockResolvedValue({
                status: 200,
                data: {},
            })

            const client = new DefaultTodoClient()
            const todoText = 'aaa'
            await client.postTodo(todoText)

            expect(spy).toHaveBeenCalledWith('/todos', { text: todoText })
        })

        it('should return created id', async () => {
            const todoId = 1

            vi.spyOn(axios, 'post').mockResolvedValue({
                status: 200,
                data: todoId,
            })

            const client = new DefaultTodoClient()
            const actual = await client.postTodo('birdman')

            expect(actual).toBe(todoId)
        })
    })
})
