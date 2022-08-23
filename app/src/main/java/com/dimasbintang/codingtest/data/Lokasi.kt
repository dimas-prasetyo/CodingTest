package com.dimasbintang.codingtest.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lokasiTable")
class Lokasi(
    @ColumnInfo val name: String,
    @ColumnInfo val latitude: String,
    @ColumnInfo val longtitude: String,
    @ColumnInfo val address: String,
    @ColumnInfo val city: String,
    @ColumnInfo val zip_code: String,
    @ColumnInfo val status: Boolean
    ) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
