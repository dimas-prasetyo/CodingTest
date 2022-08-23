package com.dimasbintang.codingtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.dimasbintang.codingtest.data.AppDatabase

class TempDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp_detail)

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database-name").build()
    }
}