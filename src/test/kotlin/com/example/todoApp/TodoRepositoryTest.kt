package com.example.todoApp

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest(
    properties = [
        "spring.jpa.hibernate.ddl-auto: create-drop"
    ]
)
class TodoRepositoryTest(@Autowired val todoRepository: TodoRepository) {

    @Test
    fun TodoObjの保存ができる(
    ) {
        val todo1 = Todo(123, "john")
        val todo2 = Todo(456, "jay")
        todoRepository.save(todo1) // ORM Object Relation Mapping
        todoRepository.save(todo2)

        // get todos: [Todo1, Todo2, Todo3, ...]
        val todos: List<Todo> = todoRepository.findAll()

        // todos == todos
        assertThat(todos.count(), equalTo(2))
        assertThat(todos[0].id, equalTo(todo1.id))
        assertThat(todos[0].text, equalTo(todo1.text))
        assertThat(todos[1].id, equalTo(todo2.id))
        assertThat(todos[1].text, equalTo(todo2.text))
    }
}