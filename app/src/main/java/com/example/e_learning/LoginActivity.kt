package com.example.e_learning

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var mainLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val moveRegis = Intent(this@LoginActivity, RegisActivity::class.java)

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

            //password dengan npm ages
            if (username == "admin" && password =="0994"){
                checkLogin=true
            } else if (password.isNotEmpty() && username.isNotEmpty() && username != "admin" && password !="0994"){
                checkLogin=false
                val builder : AlertDialog.Builder = AlertDialog.Builder(this@LoginActivity)
                builder.setMessage("Anda Belum Memiliki Akun! Silahkan register terlebih dahulu")
                    .setPositiveButton("YES", object : DialogInterface.OnClickListener{
                        override fun onClick(dialogInterface: DialogInterface, i : Int) {
                            startActivity(moveRegis)
                        }
                    })
                    .show()
            }
            if (!checkLogin) return@setOnClickListener
            val moveHome = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(moveHome)

        }
    }
}