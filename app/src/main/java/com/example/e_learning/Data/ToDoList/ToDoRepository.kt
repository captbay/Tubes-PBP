//package com.example.e_learning.Data.ToDoList
//
//import androidx.lifecycle.LiveData
//
//class ToDoRepository(private val toDoListDAO: ToDoListDAO) {
//
//    val readToDoListDAO : LiveData<List<ToDoList>> = toDoListDAO.readAllToDao()
//
//    suspend fun addToDo(toDoList: ToDoList){
//        toDoListDAO.addToDo(toDoList)
//    }
//}
//
////Repository a abstract class to access to multiple data source (best practice
////for code seaparation & architecture)