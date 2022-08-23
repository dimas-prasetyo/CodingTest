package com.dimasbintang.codingtest.data

import androidx.room.*

@Dao
interface LokasiDao {
    @Insert
    fun insertLokasi(lokasi: Lokasi)

    @Delete()
    fun delete(lokasi: Lokasi)

    @Query("SELECT * from lokasi ORDER BY id ASC")
    fun getAll(): List<Lokasi>
}