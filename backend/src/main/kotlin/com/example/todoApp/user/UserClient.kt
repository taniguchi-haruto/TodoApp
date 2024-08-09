package com.example.todoApp.user

import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class UserClient(val restClient: RestClient) {
    fun fetchUsers(): List<User> {
        val endpoint = "https://jsonplaceholder.typicode.com/users"

        return restClient.get()
            .uri(endpoint)
            .retrieve()
            .body<List<User>>()!!
    }
}