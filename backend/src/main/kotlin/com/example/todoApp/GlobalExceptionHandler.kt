package com.example.todoApp

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(TodoNotFoundException::class)
    @ResponseStatus(NOT_FOUND)
    fun handleTodoNotFoundException(ex: TodoNotFoundException): String {
        log.error(ex.message, ex)
        return ex.message!!
    }
}