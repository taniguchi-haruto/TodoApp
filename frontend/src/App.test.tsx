import { expect, it, describe } from 'vitest'
import { render, screen } from '@testing-library/react'
import App from './App.tsx'

describe('App Tests', () => {
    it('should display app title', () => {
        render(<App />) // (1)

        expect(
            screen.getByRole('heading', { name: 'Todo App' })
        ).toBeInTheDocument()
    })
})
