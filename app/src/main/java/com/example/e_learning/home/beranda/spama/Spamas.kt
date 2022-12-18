package com.example.e_learning.home.beranda.spama


class Spamas(var namaSpama:String, var poinSpama:String, var ketSpama: String, var tglDibuat: String, var status: Int, var user_id: Int) {
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