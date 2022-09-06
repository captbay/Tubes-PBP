package com.example.e_learning

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity() {
    //Atribut yang akan dipakai
    private lateinit var inputUsername : TextInputLayout
    private lateinit var inputPassword : TextInputLayout
    private lateinit var mainLayout : ConstraintLayout


    fun initComponents()
    {
        //Hubungkan variabel dengan view di layout
        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        mainLayout = findViewById(R.id.loginLayout)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Ubah Title pada AppBar Aplikasi
        setTitle("User Login")


        initComponents()
        val btnClear : Button = findViewById(R.id.btnClear)
        val btnLogin : Button = findViewById(R.id.btnLogin)
        val btnRegister : Button = findViewById(R.id.btnRegister)
        // Aksi btnClear ketika di klik
        btnClear.setOnClickListener{
            inputUsername.getEditText()?.setText("")
            inputPassword.getEditText()?.setText("")

            // Memunculkan SnackBar
            Snackbar.make(mainLayout, "Text Cleared Success", Snackbar.LENGTH_LONG).show()
        }

        btnLogin.setOnClickListener(View.OnClickListener {
            var checkLogin = false // 0
            val username : String = inputUsername.getEditText()?.getText().toString()
            val password : String = inputPassword.getEditText()?.getText().toString()

            //Pengecekan apakah inputan username kosong
            if(username.isEmpty())
            {
                inputUsername.setError("username must be filled with text")
                checkLogin = false
            }

            //Pengecekan apakah Inputan Password kosong
            if(username.isEmpty())
            {
                inputPassword.setError("Password must be filled with text")
                checkLogin = false
            }

            if(username == "admin" && password == "0541") checkLogin = true // jika username sesuai check login bernilai true
            if(!checkLogin) return@OnClickListener //jika tidak masih false if(!checklogin !=false) -> true !=false --> jump
            val moveHome = Intent(this@LoginActivity, HomeActivity::class.java) //pindah ke home activity
            startActivity(moveHome)
        })

        btnRegister.setOnClickListener {
            val moveRegister = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(moveRegister)
        }
    }
}