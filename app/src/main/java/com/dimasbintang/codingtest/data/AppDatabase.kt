package com.dimasbintang.codingtest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/*
@Database(entities = [Lokasi::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lokasiDao(): LokasiDao
}*/

@Database(entities = [Lokasi::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lokasiDao(): LokasiDao

    companion object{

        @Volatile
        private var INSTANCE: AppDatabase? =null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database").build()
                INSTANCE = instance
                instance
            }

        }
    }
}
