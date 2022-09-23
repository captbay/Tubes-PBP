package com.example.e_learning.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Presensi::class],
    version = 1
)

abstract class PresensiDB : RoomDatabase() {
    abstract  fun presensiDAO() : PresensiDAO

    companion object{
        @Volatile private var instance : PresensiDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?:buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PresensiDB::class.java,
            "tubes.db"
            ).build()
    }
}