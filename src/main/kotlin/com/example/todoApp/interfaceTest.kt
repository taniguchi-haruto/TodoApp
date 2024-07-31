package com.example.todoApp

interface DataBase {
    fun getData():String
    fun setData(data:String)
}
class TestDatabase: DataBase {
    override fun getData(): String {
      return "test"
    }

    override fun setData(data: String) {

    }
}
class DummyDatabase: DataBase {
    private var mydata: String = ""

    override fun setData(data: String) {
        this.mydata = data
    }

    override fun getData(): String {
        return this.mydata
    }
}
class RealDatabase: DataBase {
    override fun setData(data: String) {
        ///???
    }

    override fun getData(): String {
        return ""
    }
}
fun useDatabase(somethingDatabase: DataBase) {
    //val somethingDatabase: Database = DummyDatabase2()
    println("##################################")
    println(somethingDatabase)
    println("#############################")

    somethingDatabase.getData()
    somethingDatabase.setData("test")
}
