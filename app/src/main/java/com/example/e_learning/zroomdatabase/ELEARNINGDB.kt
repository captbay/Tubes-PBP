//package com.example.e_learning.zroomdatabase
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.example.e_learning.home.profile.dataprofile.Profile
//import com.example.e_learning.home.profile.dataprofile.ProfileDAO
////import com.example.e_learning.data.todoList.ToDoListDAO
//import kotlinx.coroutines.InternalCoroutinesApi
////@Database(entities = [ToDoList::class,Profile::class], version = 3)
//@Database(entities = [Profile::class], version = 4)
//abstract class ELEARNINGDB : RoomDatabase() {
//
////    abstract fun ToDoListDAO() : ToDoListDAO
//    abstract fun profileDAO() : ProfileDAO
//    companion object
//    {
//        @Volatile private var instance : ELEARNINGDB? = null
//        private val LOCK = Any()
//
//        //       Ada error disini
//        @OptIn(InternalCoroutinesApi::class)
//        operator fun invoke(context: Context) = instance ?: kotlinx.coroutines.internal.synchronized(
//            LOCK
//        ) {
//            instance ?: buildDatabase(context).also {
//                instance = it
//            }
//        }
//
//        private fun buildDatabase(context: Context) = Room.databaseBuilder(
//            context.applicationContext, ELEARNINGDB::class.java,"tubes.db"
//        ).fallbackToDestructiveMigration().build()
//    }
//}
//
//
//
////  Code Sebelumnya, coba code baru yang lebih mudah dipahami
////        private val LOCK = Any()
////
////        operator fun invoke(context: Context) = instance ?:
////        synchronized(LOCK){
////            instance ?: buildDatabase(context).also{
////                instance = it
////            }
////        }
////
////        private fun buildDatabase(context: Context) =
////
////            Room.databaseBuilder(
////                context.applicationContext,
////                TodoDB::class.java,
////            "tubes.db"
////            ).fallbackToDestructiveMigration().build()
//
//
//
////    abstract  fun todoDAO() : ToDoListDAO
//////
////    companion object{
////        @Volatile private var INSTANCE : TodoDB? = null
////
////
////        fun getDatabase(context: Context): TodoDB{
////            val tempInstance = INSTANCE
////            if(tempInstance != null)
////            {
////                return tempInstance
////            }
////
////            synchronized(this)
////            {
////                val instance = Room.databaseBuilder(
////                    context.applicationContext,
////                    TodoDB::class.java,
////                    "tubes.db"
////                ).fallbackToDestructiveMigration().build()
////                INSTANCE = instance
////                return instance
////            }
////        }
////
////    }