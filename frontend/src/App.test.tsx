import { expect, it, describe } from 'vitest'
import { render, screen, within } from '@testing-library/react'
import App from './App.tsx'
import { SpyStubTodoClient } from './SpyStubTodoClient.ts'
import { Todo } from './TodoModel.ts'
import { userEvent } from '@testing-library/user-event'

describe('App Tests', () => {
    it('should display app title', () => {
        render(<App todoClient={new SpyStubTodoClient()} />)

        expect(
            screen.getByRole('heading', { name: 'Todo App' })
        ).toBeInTheDocument()
    })

    it('should call TodoClient getTodos()', () => {
        // spy test
        const spyStubTodoClient = new SpyStubTodoClient()

        render(<App todoClient={spyStubTodoClient} />)

        expect(spyStubTodoClient.getTodosCalled).toBe(true)
    })

    it('should display fetched list of todo', async () => {
        const spyStubTodoClient = new SpyStubTodoClient()
        // stub
        spyStubTodoClient.getTodosReturnValue = [
            {
                id: 1,
                text: 'Learn Kotlin',
            },
        ] as Todo[]

        render(<App todoClient={spyStubTodoClient} />)

        expect(await screen.findByText(1)).toBeInTheDocument()
        expect(screen.getByText('Learn Kotlin')).toBeInTheDocument()
    })

    it('should display fetched list of todo v2', async () => {
        const spyStubTodoClient = new SpyStubTodoClient()
        // stub
        spyStubTodoClient.getTodosReturnValue = [
            {
                id: 1,
                text: 'Learn Kotlin',
            },
            {
                id: 2,
                text: 'Learn Kotlin',
            },
        ] as Todo[]

        render(<App todoClient={spyStubTodoClient} />)

        expect(await screen.findByText(1)).toBeInTheDocument()
        expect(screen.getByText(2)).toBeInTheDocument()
        // expect(screen.getByText('Learn Kotlin')).toBeInTheDocument()

        const texts = screen.queryAllByText('Learn Kotlin')
        expect(texts.length).toBe(2)
    })

    it('should display fetched list of todo v3', async () => {
        const spyStubTodoClient = new SpyStubTodoClient()
        // stub
        spyStubTodoClient.getTodosReturnValue = [
            {
                id: 1,
                text: 'Learn Kotlin',
            },
            {
                id: 2,
                text: 'Learn Kotlin',
            },
        ] as Todo[]

        render(<App todoClient={spyStubTodoClient} />)

        const listItems = await screen.findAllByRole('listitem')

        expect(within(listItems[0]).getByText('1')).toBeInTheDocument()
        expect(
            within(listItems[0]).getByText('Learn Kotlin')
        ).toBeInTheDocument()

        expect(within(listItems[1]).getByText('2')).toBeInTheDocument()
        expect(
            within(listItems[1]).getByText('Learn Kotlin')
        ).toBeInTheDocument()
    })

    it('different queries', async () => {
        render(<App todoClient={new SpyStubTodoClient()} />)

        // screen.getByText('hoge') // error
        // await screen.findByText('hogehoge') // error
        screen.queryByText('hoge') // null
    })

    it('should not display Hello text', async () => {
        render(<App todoClient={new SpyStubTodoClient()} />)

        // await screen.findByRole('listitem')

        // option 1
        expect(screen.queryByText('Hello')).toBeNull()
        // option 2
        expect(screen.queryByText('Hello')).not.toBeInTheDocument()
    })

    describe('Delete button Tests', () => {
        it('should display a delete button for each todo item', async () => {
            const spyStubTodoClient = new SpyStubTodoClient()
            spyStubTodoClient.getTodosReturnValue = [
                {
                    id: 1,
                    text: 'Learn Kotlin',
                },
            ] as Todo[]
            render(<App todoClient={spyStubTodoClient} />)

            const listItems = await screen.findAllByRole('listitem')

            expect(
                within(listItems[0]).getByRole('button', { name: 'Delete' })
            ).toBeInTheDocument()
        })

        it('should call todoClient deleteTodo when delete button is clicked', async () => {
            const spyStubTodoClient = new SpyStubTodoClient()
            spyStubTodoClient.getTodosReturnValue = [
                {
                    id: 1,
                    text: 'Learn Kotlin',
                },
            ] as Todo[]

            render(<App todoClient={spyStubTodoClient} />)

            const deleteButton = await screen.findByRole('button', {
                name: 'Delete',
            })

            await userEvent.click(deleteButton)

            expect(spyStubTodoClient.deleteTodoCalled).toBe(true)
            expect(spyStubTodoClient.deleteTodoId).toBe(1)
        })

        it('should not display the deleted todo when delete button is clicked', async () => {
            const spyStubTodoClient = new SpyStubTodoClient()
            spyStubTodoClient.getTodosReturnValue = [
                {
                    id: 1,
                    text: 'Learn Kotlin',
                },
            ] as Todo[]

            render(<App todoClient={spyStubTodoClient} />)

            const deleteButton = await screen.findByRole('button', {
                name: 'Delete',
            })

            await userEvent.click(deleteButton)

            expect(screen.queryByText(1)).toBe(null)
            expect(screen.queryByText('Learn Kotlin')).toBe(null)
            expect(screen.queryByRole('button', { name: 'Delete' })).toBe(null)
        })
    })
})
