package com.example.e_learning.home

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.e_learning.home.tugas.TugasFragment
import com.example.e_learning.R
import com.example.e_learning.home.beranda.BerandaFragment
import com.example.e_learning.home.kelas.KelasFragment
import com.example.e_learning.home.profile.ProfileFragment
import com.google.android.material.navigation.NavigationBarView
import com.master.permissionhelper.PermissionHelper
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import nl.joery.animatedbottombar.AnimatedBottomBar


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
       // supportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Logger.addLogAdapter(AndroidLogAdapter())
        //Untuk Default Fragment
        if(savedInstanceState ==null)
        {
            changeFragment(BerandaFragment())
        }
        val bottomNav : AnimatedBottomBar = findViewById(R.id.bottom_navigation)

        val permissionHelper = PermissionHelper(this, arrayOf(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE
            ,Manifest.permission.MANAGE_EXTERNAL_STORAGE,Manifest.permission.INTERNET ), 100)
        permissionHelper.requestAll {
            Logger.d("Berhasil Request")
        }
        // , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONEs



        bottomNav.onTabSelected = { item ->
            when(item.id){
                R.id.tab_beranda -> {
                    changeFragment(BerandaFragment())
                    true
                }
                R.id.tab_kelas -> {
                    changeFragment(KelasFragment())
                    true
                }
                R.id.tab_tugas -> {
                    changeFragment(TugasFragment())
                    true
                }
                R.id.tab_profile -> {

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