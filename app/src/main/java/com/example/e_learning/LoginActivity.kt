package com.example.e_learning

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var mainLayout: ConstraintLayout



    lateinit var Vusername: String
    lateinit var Vpassword: String
    lateinit var Vmail: String
    lateinit var VtglLahir: String
    lateinit var VnomorTelp: String

    private lateinit var username: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var tglLahir: TextInputEditText
    private lateinit var nomorTelp: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val moveRegis = Intent(this@LoginActivity, RegisActivity::class.java)

        getBundle()


        // ubah title pada app bar aplikasi
        setTitle("User Login")

        // hubungkan variabble dengan view di layoutnya
        inputUsername=findViewById(R.id.inputLayoutUsername)
        inputPassword=findViewById(R.id.inputLayoutPassword)
        mainLayout=findViewById(R.id.mainLayout)

        val btnRegis: Button = findViewById(R.id.btnRegis)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        //Aksi Btn Clear ketika di klik
        btnRegis.setOnClickListener {
//            inputUsername.getEditText()?.setText("")
//            inputPassword.getEditText()?.setText("")
//
//            //memunculkan snackbar
//            Snackbar.make(mainLayout, "Text Cleard Success", Snackbar.LENGTH_LONG).show()

            startActivity(moveRegis)
        }

        //aksi pada btn login
        btnLogin.setOnClickListener {
            var checkLogin = false
            val username: String = inputUsername.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()

//            inputPassword.getEditText()?.setText(Vusername)
            //cek input kosong
            if(username.isEmpty()){

                inputUsername.setError("Username harus diisi ya!!!")
                checkLogin=false
            }else{
                inputUsername.setError(null)
            }

            if(password.isEmpty()){
                inputPassword.setError("Password harus diisi ya!!!")
                checkLogin=false
            }else{
                inputPassword.setError(null)
            }

            //kondisi benar dan salah
            if (username == Vusername && password == Vpassword){
                checkLogin=true
                val moveHome = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(moveHome)
            } else {
                checkLogin=false
                Toast.makeText(this,"Anda gagal login, silahkan daftar dulu", Toast.LENGTH_SHORT).show()
                startActivity(moveRegis)
            }

        }
    }

    fun getBundle(){
        //bundle
        val mBundle = intent.extras
        if (mBundle != null){
            Vusername = mBundle.getString("username")!!
            Vpassword = mBundle.getString("password")!!
        }

    }
}