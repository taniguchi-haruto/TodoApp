package com.example.todoApp.user

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(val userClient : UserClient) {

    @GetMapping
    fun users() : List<User> {
        return userClient.fetchUsers()
    }
}