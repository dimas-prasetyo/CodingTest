package com.dimasbintang.codingtest.data

import androidx.lifecycle.LiveData

class LokasiRepository(private val lokasiDao: LokasiDao) {
    val allLokasi: LiveData<List<Lokasi>> = lokasiDao.getAll()

    val activeLokasi: LiveData<List<Lokasi>> = lokasiDao.getLokasiByStatus(1)
    val inactiveLokasi: LiveData<List<Lokasi>> = lokasiDao.getLokasiByStatus(0)

    suspend fun insert(lokasi: Lokasi){
        lokasiDao.insertLokasi(lokasi)
    }

    suspend fun delete(lokasi: Lokasi){
        lokasiDao.delete(lokasi)
    }

    suspend fun update(lokasi: Lokasi){
        lokasiDao.update(lokasi)
    }
}