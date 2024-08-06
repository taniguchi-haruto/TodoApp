package com.example.todoApp

import com.example.todoApp.dto.NewTodoRequest
import com.example.todoApp.dto.TodoResponse
import com.example.todoApp.dto.UpdateTodoRequest
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DefaultTodoService(val todoRepository: TodoRepository) : TodoService {
    val log = LoggerFactory.getLogger(DefaultTodoService::class.java)

    override fun todos(): List<TodoResponse> {

        val todoEntities = todoRepository.findAll()
        return todoEntities.map {
            TodoResponse(it.id, it.text)
        }
    }

    override fun create(newTodoRequest: NewTodoRequest): Long {
        val newTodoEntity = todoRepository.save(TodoEntity(text = newTodoRequest.text))

        return newTodoEntity.id
    }

    override fun update(id: Long, updateTodoRequest: UpdateTodoRequest): TodoResponse {
        val foundTodo: TodoEntity? = todoRepository.findByIdOrNull(id)

        if (foundTodo == null) {
            val err = "no todo found for id $id"
//            log.trace(err)
//            log.debug(err)
//            log.info(err)
//            log.warn(err)
            log.error(err)
            throw RuntimeException(err)
        }

        val updated = todoRepository.save(TodoEntity(foundTodo.id, updateTodoRequest.text))
        return TodoResponse(updated.id, updated.text)
    }

    override fun delete(id: Long):Long{
        val foundTodo: TodoEntity =
            todoRepository.findByIdOrNull(id) ?: throw RuntimeException("no todo found for id $id")
            todoRepository.deleteById(foundTodo.id)
        return foundTodo.id
    }

}