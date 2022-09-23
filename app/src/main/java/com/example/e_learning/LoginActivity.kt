package com.example.e_learning

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlin.concurrent.thread
import kotlin.math.log


class LoginActivity : AppCompatActivity() {
    //Atribut yang akan dipakai
    private lateinit var inputUsername : TextInputLayout
    private lateinit var inputPassword : TextInputLayout
    private lateinit var mainLayout : ConstraintLayout
    var mbUsername : String? = null
    var mbPassword: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //splashscreen
        Thread.sleep(2000)
        val splashScreen = installSplashScreen()
        //
        setContentView(R.layout.activity_login)
        getBundle()
        initComponents()
        //Init Button --> bertipe val karena kegunaannya tidak berubah
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

        // Aksi btnLogin ketika di klik
        btnLogin.setOnClickListener  OnClickListener@{

            //Inisialisasi kondisi
            var checkLogin = false // 0
            val username : String = inputUsername.getEditText()?.getText().toString()
            val password : String = inputPassword.getEditText()?.getText().toString()

            //Pengecekan apakah inputan username kosong
            if(username.isEmpty()) {
                inputUsername.requestFocus()
                inputUsername.setError("username must be filled with text")
                checkLogin = false
                Log.i("Test", "Pengecekan Username Kosong Sukses")
            }else {
                Log.i("Test", "Username tidak kosong : "+username)
                inputUsername.setError(null)
            }

            //Pengecekan apakah Inputan Password kosong
            if(password.isEmpty()) {
                inputPassword.setError("password must be filled with text")
                Snackbar.make(mainLayout,"Passwordnya kosong boss",Snackbar.LENGTH_SHORT).show()
                checkLogin = false
                Log.i("Test","Pengecekan Password Kosong Sukses ")
            }else{
                Log.i("Test", "Password Tidak Kosong : "+password)
                inputPassword.setError(null)
            }


            if(username.isNotEmpty() && password.isNotEmpty()) {
                getBundle()
                val moveHome =Intent(this@LoginActivity,HomeActivity::class.java)

                if(username ==mbUsername && password==mbPassword) {
                    startActivity(moveHome)
                    Log.i("Test", "Pengecekan " + mbUsername)
                }else if(username!=mbUsername || password!=mbPassword){
                    Snackbar.make(mainLayout,"Username / Password Salah",Snackbar.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                else{
                    Snackbar.make(mainLayout,"Silakan Registrasi Terlebih Dahulu",Snackbar.LENGTH_SHORT).show()
                    return@OnClickListener
                }

            }

        }

        //btnRegister untuk pindah ke ActivityRegister
        btnRegister.setOnClickListener {
            inputPassword.setError("")
            val moveRegister = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(moveRegister)
        }
    }


    fun getBundle()
    {
        val mBundle = intent.extras
        if(mBundle !=null)
        {
            mbUsername =  mBundle.getString("username") //!! kalau pakai tanda seru -> run 1x doang.
            mbPassword =  mBundle.getString("password") // ..
        }
        Log.i("Test","Success Get Bundle")
    }

    fun initComponents()
    {
        //overridePendingTransition(0,1) --> niatannya mau buat reset condition
        //Hubungkan variabel dengan view di layout
        //Ubah Title pada AppBar Aplikasi
        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        mainLayout = findViewById(R.id.loginLayout)
        getSupportActionBar()?.hide();
        val mBundle = intent.extras
        if(mBundle!=null)
        {
            inputUsername.getEditText()?.setText(mbUsername)
//            inputPassword.getEditText()?.setText(mbPassword)
        }

    }
}