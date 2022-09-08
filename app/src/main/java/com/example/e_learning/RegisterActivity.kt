package com.example.e_learning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {
    //Component View
    private lateinit var inputUsername : TextInputLayout
    private lateinit var inputPassword : TextInputLayout
    private lateinit var inputEmail : TextInputLayout
    private lateinit var inputTanggalLahir : TextInputLayout
    private lateinit var inputNomorTelepon : TextInputLayout
    private lateinit var btnRegister : Button
    private lateinit var btnCancel : Button
//    private lateinit var btn

    private fun initComponent()
    {
        setTitle("Register Akun")
        inputUsername = findViewById(R.id.inputLayoutUsernameRegister)
        inputPassword =  findViewById(R.id.inputLayoutPasswordRegister)
        inputEmail = findViewById(R.id.inputLayoutEmailRegister)
        inputTanggalLahir =  findViewById(R.id.inputLayoutTanggalLahir)
        inputNomorTelepon = findViewById(R.id.inputLayoutTelp)
        btnRegister = findViewById(R.id.btnRegisterAkun)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initComponent()



        btnRegister.setOnClickListener {
            var checkRegisterInput = false
            val username : String = inputUsername.getEditText()?.getText().toString()
            val password : String = inputPassword.getEditText()?.getText().toString()
            val email : String = inputEmail.getEditText()?.getText().toString()
            val tanggalLahir : String = inputTanggalLahir.getEditText()?.getText().toString()
            val nomorTelp : String = inputNomorTelepon.getEditText()?.getText().toString()

            //Cek masing-masing inputan & pastikan tidak kosong
            if(username.isEmpty())
            {
                inputUsername.setError("Username Harus diisi")
            }
            if (password.isEmpty()){
                inputPassword.setError("Password Harus diisi")
            }
            if (email.isEmpty()){
                inputEmail.setError("Password Harus diisi")
            }
            if (tanggalLahir.isEmpty()){
                inputTanggalLahir.setError("Password Harus diisi")
            }
            if (nomorTelp.isEmpty()){
                inputNomorTelepon.setError("Password Harus diisi")
            }
            if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() && tanggalLahir.isNotEmpty() && nomorTelp.isNotEmpty()){
                checkRegisterInput=true
            }
            if (!checkRegisterInput){
                return@setOnClickListener
            }
            val intent = Intent(this, LoginActivity::class.java)
            val mBundle : Bundle?= Bundle()
            mBundle?.putString("username", username)
            mBundle?.putString("password", password)
            mBundle?.putString("email" , email)
            mBundle?.putString("tanggalLahir", tanggalLahir)
            mBundle?.putString("nomorTelp",nomorTelp)

            if (mBundle != null) {
                intent.putExtras(mBundle)
            }
            Toast.makeText(this,"Berhasil Mendaftarkan Akun",Toast.LENGTH_SHORT).show()
            startActivity(intent)

        }
    }
}