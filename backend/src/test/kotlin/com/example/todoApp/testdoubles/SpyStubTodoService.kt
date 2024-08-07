package com.example.todoApp.testdoubles

import com.example.todoApp.TodoService
import com.example.todoApp.dto.NewTodoRequest
import com.example.todoApp.dto.TodoResponse
import com.example.todoApp.dto.UpdateTodoRequest


class SpyStubTodoService : TodoService {

    var todosCalledTimes: Int = 0
    var todosCalled: Boolean = false
    var todosReturnValue: List<TodoResponse> = emptyList()


    var createReturnValue: Long = 0
    var createCalled: Boolean = false
    var createCalledTimes: Int = 0

    override fun todos(): List<TodoResponse> {
        this.todosCalled = true
        todosCalledTimes++
        return this.todosReturnValue
    }

    override fun todo(id: Long): TodoResponse {
        TODO("Not yet implemented")
    }

    override fun create(newTodoRequest: NewTodoRequest): Long {
        this.createCalled = true
        createCalledTimes++
        return this.createReturnValue
    }

    override fun update(id: Long, updateTodoRequest: UpdateTodoRequest): TodoResponse {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) :Long{
        TODO("Not yet implemented")
    }

    fun stubForTodos(todos: List<TodoResponse>) {
        // set the return value of todos()
        this.todosReturnValue = todos
    }


    fun stubForCreate(newId: Long) {
        // set the return value of create()
        this.createReturnValue = newId
    }
}