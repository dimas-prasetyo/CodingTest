package com.dimasbintang.codingtest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dimasbintang.codingtest.data.AppDatabase
import com.dimasbintang.codingtest.data.Lokasi
import com.dimasbintang.codingtest.data.LokasiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LokasiViewModel(application: Application): AndroidViewModel(application) {
    val allLokasi: LiveData<List<Lokasi>>
    val activeLokasi: LiveData<List<Lokasi>>
    val inActiveLokasi: LiveData<List<Lokasi>>
    val repository: LokasiRepository

    init {
        val dao = AppDatabase.getDatabase(application).lokasiDao()
        repository = LokasiRepository(dao)
        allLokasi = repository.allLokasi
        activeLokasi = repository.activeLokasi
        inActiveLokasi = repository.inactiveLokasi
    }

    fun deleteLokasi(lokasi: Lokasi) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(lokasi)
    }

    fun updateLokasi(lokasi: Lokasi) = viewModelScope.launch(Dispatchers.IO){
        repository.update(lokasi)
    }

    fun addLokasi(lokasi: Lokasi) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(lokasi)
    }


}