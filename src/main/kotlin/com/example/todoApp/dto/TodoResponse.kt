package com.example.todoApp.dto

data class TodoResponse(val id: Long = 0, val text: String = "") {
    // option 1) no argument constructor
    // This is same as setting default values
    //    constructor(): this(0, "")  // constructor() means no argument constructor
}
