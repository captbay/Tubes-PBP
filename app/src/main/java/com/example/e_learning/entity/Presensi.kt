package com.example.e_learning.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Presensi (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val tanggal : String,
    val kehadiran : String, //tipe kehadiran H, I, S
    val catatan : String
)
//