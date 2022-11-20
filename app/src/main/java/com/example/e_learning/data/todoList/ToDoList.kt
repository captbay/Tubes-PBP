package com.example.e_learning.data.todoList


class ToDoList(var judul:String, var pesan:String, var tglDibuat: String, var tglDeadline: String, var status: Int) {
    var id: Long? = null
}

//@Entity(tableName = "todo_list")
//data class ToDoList (
//    @PrimaryKey(autoGenerate = true)
//    val id : Int,
//    val tanggal : String,
//    val tanggalDeadline : String,
//    val judul: String,
//    val pesan : String,
//    val status : Boolean
//)
//