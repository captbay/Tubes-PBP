package com.example.e_learning.entity

import com.example.e_learning.R

class kelas (var mataPelajaran:String, var sesiKelas:String, var tahunAjaran:String, var guruPengajar:String, var photo:Int) {
    companion object{
        var litsOfKelas = arrayOf(
            kelas("Matematika","07:00-09:00", "2022", "Pak Budi", R.drawable.pakbudi),
            kelas("IPA","07:00-09:00", "2022", "Pak Budi", R.drawable.pakbudi),
            kelas("Bahasa Inggris","07:00-09:00", "2022", "Pak Budi", R.drawable.pakbudi),
            kelas("Bahasa Indonesia","07:00-09:00", "2022", "Pak Budi", R.drawable.pakbudi),
            kelas("IPS","07:00-09:00", "2022", "Pak Budi", R.drawable.pakbudi),
            kelas("Pendidikan Jasmani","07:00-09:00", "2022", "Pak Budi", R.drawable.pakbudi),
            kelas("PKN","07:00-09:00", "2022", "Pak Budi", R.drawable.pakbudi),
            kelas("Agama","07:00-09:00", "2022", "Pak Budi", R.drawable.pakbudi),
            kelas("Muatan Lokal","07:00-09:00", "2022", "Pak Budi", R.drawable.pakbudi),
            kelas("Seni Budaya","07:00-09:00", "2022", "Pak Budi", R.drawable.pakbudi),
        )
    }
}