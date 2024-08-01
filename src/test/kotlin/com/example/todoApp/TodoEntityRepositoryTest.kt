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
class TodoEntityRepositoryTest(@Autowired val todoRepository: TodoRepository) {

    @Test
    fun TodoObjの保存ができる(
    ) {
        val todoEntity1 = TodoEntity( 0, "john")
        val todoEntity2 = TodoEntity(0, "jay")
        todoRepository.save(todoEntity1) // ORM Object Relation Mapping
        todoRepository.save(todoEntity2)

        // get todos: [Todo1, Todo2, Todo3, ...]
        val todoEntities: List<TodoEntity> = todoRepository.findAll()

        // todos == todos
        assertThat(todoEntities.count(), equalTo(2))
        assertThat(todoEntities[0].id, equalTo(todoEntity1.id))
        assertThat(todoEntities[0].text, equalTo(todoEntity1.text))
        assertThat(todoEntities[1].id, equalTo(todoEntity2.id))
        assertThat(todoEntities[1].text, equalTo(todoEntity2.text))
    }
}