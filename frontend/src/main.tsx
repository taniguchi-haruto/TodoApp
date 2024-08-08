import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import { DefaultTodoClient } from './DefaultTodoClient.ts'

ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
        <App todoClient={new DefaultTodoClient()} />
    </React.StrictMode>
)
