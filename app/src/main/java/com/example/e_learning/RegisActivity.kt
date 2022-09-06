package com.example.e_learning

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class RegisActivity : AppCompatActivity() {

//    private lateinit var inputUsername: TextInputLayout
//    private lateinit var inputPassword: TextInputLayout
//    private lateinit var mainLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regis)

//        val moveLogin = Intent(this@RegisActivity, LoginActivity::class.java)
//
//        // ubah title pada app bar aplikasi
//        setTitle("User Daftar")
//
//        // hubungkan variabble dengan view di layoutnya
//        inputUsername = findViewById(R.id.inputLayoutUsername)
//        inputPassword = findViewById(R.id.inputLayoutPassword)
//        mainLayout = findViewById(R.id.mainLayout)
//
//        val btnRegis: Button = findViewById(R.id.btnGas)
//
//        //Aksi Btn Clear ketika di klik
//        btnRegis.setOnClickListener {
//            var checkLogin = false
//            val username: String = inputUsername.getEditText()?.getText().toString()
//            val password: String = inputPassword.getEditText()?.getText().toString()
//
//            //cek input kosong
//            if(username.isEmpty()){
//                inputUsername.setError("Username harus diisi ya!!!")
//                checkLogin=false
//            }else{
//                inputUsername.setError(null)
//            }
//
//            if(password.isEmpty()){
//                inputPassword.setError("Password harus diisi ya!!!")
//                checkLogin=false
//            }else{
//                inputPassword.setError(null)
//            }
//
//            //password dengan npm ages
//            if (username == "admin" && password =="0994"){
//                checkLogin=true
//                Snackbar.make(mainLayout, "Anda Telah Berhasil Daftar", Snackbar.LENGTH_LONG).show()
//
//                startActivity(moveLogin)
//            } else if (password.isNotEmpty() && username.isNotEmpty() && username != "admin" && password !="0994"){
//                checkLogin=false
//                Snackbar.make(mainLayout, "Anda Gagal Daftar", Snackbar.LENGTH_LONG).show()
//
//            }
//            if (!checkLogin) return@setOnClickListener
        }


}