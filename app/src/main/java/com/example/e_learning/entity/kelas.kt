package com.example.e_learning.entity

import com.example.e_learning.R

class kelas (var mataPelajaran:String, var sesiKelas:String, var tahunAjaran:String, var guruPengajar:String, var photo:Int) {
    companion object{
        var litsOfKelas = arrayOf(
            kelas("Matematika","07:00-09:00", "2022", "Pak Budi", R.drawable.pakbudi),
            kelas("IPA","07:00-09:00", "2022", "Bu Desi", R.drawable.budesi),
            kelas("Bahasa Inggris","07:00-09:00", "2022", "Pak Dul", R.drawable.pakdul),
            kelas("Bahasa Indonesia","07:00-09:00", "2022", "Bu Ela", R.drawable.buela),
            kelas("IPS","07:00-09:00", "2022", "Pak Joni", R.drawable.pakjoni),
            kelas("Pendidikan Jasmani","07:00-09:00", "2022", "Bu Indah", R.drawable.buindah),
            kelas("PKN","07:00-09:00", "2022", "Pak Pras", R.drawable.pakpras),
            kelas("Agama","07:00-09:00", "2022", "Bu Lala", R.drawable.bulala),
            kelas("Muatan Lokal","07:00-09:00", "2022", "Pak Pri", R.drawable.pakpri),
            kelas("Seni Budaya","07:00-09:00", "2022", "Pak Susi", R.drawable.bususi),
        )
    }
}