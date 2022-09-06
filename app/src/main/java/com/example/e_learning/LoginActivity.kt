package com.example.e_learning

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlin.math.log


class LoginActivity : AppCompatActivity() {
    //Atribut yang akan dipakai
    private lateinit var inputUsername : TextInputLayout
    private lateinit var inputPassword : TextInputLayout
    private lateinit var mainLayout : ConstraintLayout
    lateinit var mbUsername : String
    lateinit var mbPassword : String

    fun initComponents()
    {
        overridePendingTransition(0,1)
        //Hubungkan variabel dengan view di layout
        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        mainLayout = findViewById(R.id.loginLayout)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        //Ubah Title pada AppBar Aplikasi
        setTitle("A3-Learning Login")


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
                inputUsername.requestFocus()
                inputUsername.setError("username must be filled with text")
                checkLogin = false
                Log.i("Test", "Pengecekan Username Kosong Sukses")
            }else
            {
                Log.i("Test", "Username tidak kosong : "+username)
            }

            //Pengecekan apakah Inputan Password kosong
            if(password.isEmpty())
            {
                inputPassword.setError("password must be filled with text")
                Snackbar.make(mainLayout,"Passwordnya kosong boss",Snackbar.LENGTH_SHORT).show()
                checkLogin = false
                Log.i("Test","Pengecekan Password Kosong Sukses ")
            }else{Log.i("Test", "Password Tidak Kosong : "+password) }

        })

        btnRegister.setOnClickListener {
            val moveRegister = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(moveRegister)
        }
    }

    fun getBundle()
    {
        val mBundle = intent.extras!! // !! memastikan mBundle tidak nullable
        mbUsername = mBundle.getString("username")!!
        mbPassword = mBundle.getString("password")!!


    }

}