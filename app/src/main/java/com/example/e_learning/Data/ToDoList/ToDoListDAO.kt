package com.example.e_learning.Data.ToDoList

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.e_learning.Data.ToDoList.ToDoList


@Dao
interface ToDoListDAO {
    @Insert
    suspend fun addNote(toDoList: ToDoList)
    @Update
    suspend fun updateNote(toDoList: ToDoList)
    @Delete
    suspend fun deleteNote(toDoList: ToDoList)
    @Query("SELECT * FROM todo_list")
    suspend fun getNotes() : List<ToDoList>
    @Query("SELECT * FROM todo_list WHERE id =:todo_id")
    suspend fun getNote(todo_id: Int) : List<ToDoList>
}

//


//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun addToDo(toDoList: ToDoList)
//
//    @Update
//    suspend fun updateToDo(toDoList: ToDoList)
//
//    @Delete
//    suspend fun deleteTodo(toDoList: ToDoList)
//
//    @Query("SELECT * FROM todo_list ORDER BY id ASC")
//    fun readAllToDao() : LiveData<List<ToDoList>>
