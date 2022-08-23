package com.dimasbintang.codingtest.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Lokasi::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lokasiDao(): LokasiDao
}