package com.example.todoApp

import org.junit.jupiter.api.Test

class SampleTest {

    @Test
    fun `call printHello()`() {
        // we need an instance
        val a = Sample("") // constructor
        // JS: const a = new Sample()


        // we call the method
//        Sample.printHello()
        a.printHello()
    }
}