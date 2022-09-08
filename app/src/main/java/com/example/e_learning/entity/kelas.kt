package com.example.e_learning.entity

import com.example.e_learning.R

class kelas (var mataPelajaran:String, var sesiKelas:String, var tahunAjaran:String, var guruPengajar:String, var photo:Int) {
    companion object{
        var litsOfKelas = arrayOf(
            kelas("Matematika","07:00-09:00", "2022", "Pak Budi", R.drawable.Pakbudi),
            kelas("IPA","07:00-09:00", "2022", "Pak Budi", R.drawable.Pakbudi),
            kelas("Bahasa Inggris","07:00-09:00", "2022", "Pak Budi", R.drawable.Pakbudi),
            kelas("Bahasa Indonesia","07:00-09:00", "2022", "Pak Budi", R.drawable.Pakbudi),
            kelas("IPS","07:00-09:00", "2022", "Pak Budi", R.drawable.Pakbudi),
            kelas("Pendidikan Jasmani","07:00-09:00", "2022", "Pak Budi", R.drawable.Pakbudi),
            kelas("PKN","07:00-09:00", "2022", "Pak Budi", R.drawable.Pakbudi),
            kelas("Agama","07:00-09:00", "2022", "Pak Budi", R.drawable.Pakbudi),
            kelas("Muatan Lokal","07:00-09:00", "2022", "Pak Budi", R.drawable.Pakbudi),
            kelas("Seni Budaya","07:00-09:00", "2022", "Pak Budi", R.drawable.Pakbudi),
        )
    }
}