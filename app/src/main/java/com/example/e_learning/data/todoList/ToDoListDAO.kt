//package com.example.e_learning.data.todoList
//
//import androidx.room.*
//
//
//@Dao
//interface ToDoListDAO {
//    @Insert
//    suspend fun addTodo(toDoList: ToDoList)
//    @Update
//    suspend fun updateTodo(toDoList: ToDoList)
//    @Delete
//    suspend fun deleteToDo(toDoList: ToDoList)
//    @Query("SELECT * FROM todo_list")
//    suspend fun getNotes() : List<ToDoList>
//    @Query("SELECT * FROM todo_list WHERE id =:todo_id")
//    suspend fun getNote(todo_id: Int) : List<ToDoList>
//
//}
//
////
//
//
////    @Insert(onConflict = OnConflictStrategy.IGNORE)
////    suspend fun addToDo(toDoList: ToDoList)
////
////    @Update
////    suspend fun updateToDo(toDoList: ToDoList)
////
////    @Delete
////    suspend fun deleteTodo(toDoList: ToDoList)
////
////    @Query("SELECT * FROM todo_list ORDER BY id ASC")
////    fun readAllToDao() : LiveData<List<ToDoList>>
