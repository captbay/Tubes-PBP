package com.example.e_learning.Data.Profile

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(
    entities = [Profile::class],

    version = 2
)
//test
abstract class ProfileDB : RoomDatabase() {

    abstract fun profileDAO() : ProfileDAO

    companion object
    {
        @Volatile private var instance : ProfileDB? = null
        private val LOCK = Any()

        //       Ada error disini
        @OptIn(InternalCoroutinesApi::class)
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, ProfileDB::class.java,"tubes.db"
        ).fallbackToDestructiveMigration().build()
    }
}