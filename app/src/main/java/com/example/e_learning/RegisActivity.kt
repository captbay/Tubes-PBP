package com.example.e_learning

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisActivity : AppCompatActivity() {
//    DatePickerDialog.OnDateSetListener
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var inputEmail: TextInputLayout
    private lateinit var inputTglLahir: TextInputLayout
    private lateinit var inputNmrTelp: TextInputLayout

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

//        val moveLogin = Intent(this@RegisActivity, LoginActivity::class.java)

        setTitle("User Daftar")


        val btnClear: Button = findViewById(R.id.btnClear)
        val btnGas: Button = findViewById(R.id.btnGas)

        btnClear.setOnClickListener {
            inputUsername.getEditText()?.setText("")
            inputPassword.getEditText()?.setText("")
            inputEmail.getEditText()?.setText("")
            inputTglLahir.getEditText()?.setText("")
            inputNmrTelp.getEditText()?.setText("")

            //memunculkan snackbar
            Toast.makeText(this,"akan menghapus semua data", Toast.LENGTH_SHORT).show()
        }

        btnGas.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            val mBundle = Bundle()

            mBundle.putString("username", username.text.toString())
            mBundle.putString("password", password.text.toString())
            mBundle.putString("email", email.text.toString())
            mBundle.putString("tglLahir", tglLahir.text.toString())
            mBundle.putString("nomorTelp", nomorTelp.text.toString())

            intent.putExtras(mBundle)

            Toast.makeText(this,"Anda telah berhasil daftar", Toast.LENGTH_SHORT).show()

            startActivity(intent)
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

//            tglLahir.setOnClickListener{
//                DatePickerDialog(this).show()
//            }
        }

//    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
//        Log.e("Calendar", "$year -- $month -- $dayOfMonth")
//    }


}