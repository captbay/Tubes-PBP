//package com.example.e_learning.Data.ToDoList
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//class ToDoViewModel(application: Application) : AndroidViewModel(application) {
//    public val readAllTodo : LiveData<List<ToDoList>>
//    private val repository : ToDoRepository
//    init{
//        val todoDAO = TodoDB.getDatabase(application).todoDAO()
//        repository = ToDoRepository(todoDAO)
//        readAllTodo = repository.readToDoListDAO
//    }
//
//    fun addTodo(toDoList: ToDoList)
//    {
//        viewModelScope.launch(Dispatchers.IO)
//        {
//            repository.addToDo(toDoList)
//        }
//    }
//}