package com.dimasbintang.codingtest.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface LokasiDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLokasi(lokasi: Lokasi)

    @Delete
    suspend fun delete(lokasi: Lokasi)

    @Update
    suspend fun update(lokasi: Lokasi)

    @Query("SELECT * from lokasiTable order BY id ASC")
    fun getAll(): LiveData<List<Lokasi>>

    @Query("SELECT * FROM lokasiTable WHERE status=:status")
    fun getLokasiByStatus(status: Int): LiveData<List<Lokasi>>
}
