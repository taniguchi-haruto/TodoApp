package com.example.todoApp


import org.junit.jupiter.api.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

// port = random()
// server = new TomcatServer(port)
// restTemplate = new TestRestTemplate(server)
// testapp = new TodoAppApplicationTest(restTemplate, port)
// testapp.GETリクエストはOKステータスを返す()

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	properties = [
		"spring.jpa.hibernate.ddl-auto: create-drop"
	]
)
class TodoEntityAppApplicationTests (
	@Autowired val restTemplate: TestRestTemplate,
	@Autowired val todoRepository: TodoRepository,
	@LocalServerPort val port: Int
)
{
//	lateinit var restTemplate: TestRestTemplate
//	var port: Int = 0
//
//	constructor(@Autowired restTemplate: TestRestTemplate, @LocalServerPort port: Int) {
//		this.restTemplate = restTemplate
//		this.port = port
//		println("constructor: restTemplate=$restTemplate, port=$port")
//	}

	@AfterEach
	fun cleanUp() {
		todoRepository.deleteAll()
	}

	@Test
	fun contextLoads() {
	}

	@Test
	fun `GETリクエストはOKステータスを返す`() {
		// localhost/todos に GETリクエストを発行する。
		val response = restTemplate.getForEntity("http://localhost:$port/todos", String::class.java)
		// レスポンスのステータスコードは OK である。
		println("response.statusCode=${response.statusCode}, httpStatus.OK=${HttpStatus.OK}")
		assertThat(response.statusCode, equalTo(HttpStatus.OK))
	}

	@Test
	fun `GETリクエストはTodoオブジェクトのリストを返す`() {
		// localhost/todos に GETリクエストを送り、レスポンスを Todoオブジェクトの配列として解釈する。
		val todoEntity1 = TodoEntity(1, "foo")
		val todoEntity2 = TodoEntity(2, "bar")
		todoRepository.save(todoEntity1)
		todoRepository.save(todoEntity2)

		val response: ResponseEntity<Array<TodoEntity>> = restTemplate.getForEntity("http://localhost:$port/todos", Array<TodoEntity>::class.java)
		// レスポンスの Content-Type は application/json であること。
		assertThat(response.headers.contentType, equalTo(MediaType.APPLICATION_JSON))
		// 配列は2つの要素をもつこと。
		val todos = response.body!!
		assertThat(todos.size, equalTo(2))
		// 最初の要素は id=1 であり、text が "foo" であること。
		assertThat(todos[0].id, equalTo(todoEntity1.id))
		assertThat(todos[0].text, equalTo(todoEntity1.text))
		// 次の要素は id=2 であり、text が "bar" であること。
		assertThat(todos[1].id, equalTo(todoEntity2.id))
		assertThat(todos[1].text, equalTo(todoEntity2.text))
	}

//	@Test
//	fun `todoObjを一つ返す`() {
//		// localhost/todos に GETリクエストを送り、レスポンスを Todoオブジェクトの配列として解釈する。
//		val todo1 = Todo(3, "foo")
//		todoRepository.save(todo1)
//
//		val response: ResponseEntity<Array<Todo>> = restTemplate.getForEntity("http://localhost:$port/todos", Array<Todo>::class.java)
//		// 配列は2つの要素をもつこと。
//		val todos = response.body!!
//		assertThat(todos.size, equalTo(1))
//		// 最初の要素は id=1 であり、text が "foo" であること。
//		assertThat(todos[0].id, equalTo(todo1.id))
//		assertThat(todos[0].text, equalTo(todo1.text))
//	}

	@Test
	fun `POSTリクエストはOKステータスを返す`(){
		val todos1 = TodoEntity(0,"test")
		val response = restTemplate.postForEntity("http://localhost:$port/todos",todos1, String::class.java)
//		println("response.statusCode=${response.statusCode}, httpStatus.OK=${HttpStatus.OK}")
		assertThat(response.statusCode, equalTo(HttpStatus.OK))
	}
	@Test
	fun `POSTリクエストしたらデータが保存されている`(){
		todoRepository.deleteAll()
		val todos1 = TodoEntity(0,"test")
		restTemplate.postForEntity("http://localhost:$port/todos",todos1, String::class.java)

		val todoEntities:List<TodoEntity> = todoRepository.findAll()
		assertThat(todoEntities.size, equalTo(1))
		assertThat(todoEntities[0].id, not(equalTo(0)))
		assertThat(todoEntities[0].text, equalTo("test"))
	}
}
