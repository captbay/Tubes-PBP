package com.example.e_learning.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.e_learning.Fragment.*
import com.example.e_learning.R
import com.example.e_learning.home.beranda.BerandaFragment
import com.example.e_learning.home.kelas.KelasFragment
import com.example.e_learning.home.profile.ProfileFragment
import com.google.android.material.navigation.NavigationBarView


class HomeActivity : AppCompatActivity() {
//    companion object {
//        const val LAUNCH_ADD_ACTIVITY = 123
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
       // supportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Untuk Default Fragment
        if(savedInstanceState ==null)
        {
            changeFragment(BerandaFragment())
        }
        val bottomNav : NavigationBarView = findViewById(R.id.bottom_navigation)



        bottomNav.setOnItemSelectedListener{ item ->
            when(item.itemId){
                R.id.beranda -> {
                    changeFragment(BerandaFragment())
                    true
                }
                R.id.kelas -> {
                    changeFragment(KelasFragment())
                    true
                }
                R.id.tugas -> {
                    changeFragment(TugasFragment())
                    true
                }
                R.id.profile -> {

                    changeFragment(ProfileFragment())
                    true
                }
                else->false

            }
        }
    }

    fun changeFragment(fragment : Fragment?)
    {
        if(fragment != null)
        {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, fragment)
                .commit()
        }
    }
}