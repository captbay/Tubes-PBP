package com.example.e_learning

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisActivity : AppCompatActivity() {

    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var inputEmail: TextInputLayout
    private lateinit var inputTglLahir: TextInputLayout
    private lateinit var inputNmrTelp: TextInputLayout
    private lateinit var mainLayout: ConstraintLayout
    private lateinit var username: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var tglLahir: TextInputEditText
    private lateinit var nomorTelp: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regis)


        username = findViewById(R.id.etUsername)
        password = findViewById(R.id.etPassword)
        email = findViewById(R.id.etEmail)
        tglLahir = findViewById(R.id.etTglLahir)
        nomorTelp = findViewById(R.id.etNmrTelpn)

        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        inputEmail = findViewById(R.id.inputLayoutEmail)
        inputTglLahir = findViewById(R.id.inputLayoutTglLahir)
        inputNmrTelp = findViewById(R.id.inputLayoutNmrTelp)
        mainLayout = findViewById(R.id.mainLayout)
//        val moveLogin = Intent(this@RegisActivity, LoginActivity::class.java)

        setTitle("User Daftar")


        val btnClear: Button = findViewById(R.id.btnClear)

        btnClear.setOnClickListener {
            inputUsername.getEditText()?.setText("")
            inputPassword.getEditText()?.setText("")
            inputEmail.getEditText()?.setText("")
            inputTglLahir.getEditText()?.setText("")
            inputNmrTelp.getEditText()?.setText("")

            //memunculkan snackbar
            Snackbar.make(mainLayout, "Text Cleard Success", Snackbar.LENGTH_LONG).show()
        }
//        //Aksi Btn Regis ketika di klik
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