package com.example.e_learning.home.tugas.data

import com.example.e_learning.R

class   Tugas (var namaTugas:String, var deskripsiTugas:String, var durasiTugas:String, var hadiahTugas:String) {
    companion object{
        var litsOfTugas = arrayOf(
            Tugas("Mengumpulkan Tugas UGD PBP","Mengumpulkan Tugas UGD tidak boleh telat", "6 hari lagi", "+500Pts"),
            Tugas("Mengumpulkan Tugas WEB","Mengumpulkan tidak boleh telat", "2 hari lagi", "+500Pts"),
            Tugas("Mengumpulkan Tugas PMPL","Mengumpulkan tidak boleh telat", "3 hari lagi", "+500Pts"),
            Tugas("Mengumpulkan Tugas MPPL","Mengumpulkan tidak boleh telat", "4 hari lagi", "+500Pts"),
            Tugas("Mengumpulkan Tugas Awan","Mengumpulkan tidak boleh telat", "6 hari lagi", "+500Pts"),
            Tugas("Mengumpulkan Tugas Kuliah Lapangan","Mengumpulkan tidak boleh telat", "9 hari lagi", "+500Pts"),
            Tugas("Mengumpulkan Tugas BI","Mengumpulkan tidak boleh telat", "2 hari lagi", "+500Pts"),
            Tugas("Mengumpulkan Tugas KK","Mengumpulkan tidak boleh telat", "3 hari lagi", "+500Pts"),
            Tugas("Mengumpulkan Tugas Strukdat","Mengumpulkan tidak boleh telat", "1 hari lagi", "+500Pts"),
            Tugas("Mengumpulkan Tugas Daspro","Mengumpulkan tidak boleh telat", "12 hari lagi", "+500Pts"),
        )
    }
}