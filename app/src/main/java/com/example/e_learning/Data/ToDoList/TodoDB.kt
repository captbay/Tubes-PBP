package com.example.e_learning.Data.ToDoList

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.e_learning.Data.Profile.ProfileDAO
import com.example.e_learning.Data.Profile.ProfileDB
import kotlinx.coroutines.InternalCoroutinesApi

@Database(
    entities = [ToDoList::class],
    version = 2
)

abstract class TodoDB : RoomDatabase() {

    abstract fun ToDoListDAO() : ToDoListDAO

    companion object
    {
        @Volatile private var instance : TodoDB? = null
        private val LOCK = Any()

        //       Ada error disini
        @OptIn(InternalCoroutinesApi::class)
        operator fun invoke(context: Context) = instance ?: kotlinx.coroutines.internal.synchronized(
            LOCK
        ) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, TodoDB::class.java,"tubes.db"
        ).fallbackToDestructiveMigration().build()
    }
}



//  Code Sebelumnya, coba code baru yang lebih mudah dipahami
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance ?:
//        synchronized(LOCK){
//            instance ?: buildDatabase(context).also{
//                instance = it
//            }
//        }
//
//        private fun buildDatabase(context: Context) =
//
//            Room.databaseBuilder(
//                context.applicationContext,
//                TodoDB::class.java,
//            "tubes.db"
//            ).fallbackToDestructiveMigration().build()



//    abstract  fun todoDAO() : ToDoListDAO
////
//    companion object{
//        @Volatile private var INSTANCE : TodoDB? = null
//
//
//        fun getDatabase(context: Context): TodoDB{
//            val tempInstance = INSTANCE
//            if(tempInstance != null)
//            {
//                return tempInstance
//            }
//
//            synchronized(this)
//            {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    TodoDB::class.java,
//                    "tubes.db"
//                ).fallbackToDestructiveMigration().build()
//                INSTANCE = instance
//                return instance
//            }
//        }
//
//    }