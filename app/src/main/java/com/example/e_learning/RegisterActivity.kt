package com.example.e_learning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.e_learning.databinding.ActivityRegisterBinding
import com.example.e_learning.entity.Profile
import com.example.e_learning.room.ProfileDB
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    //Component View
//    private lateinit var inputUsername : TextInputLayout
//    private lateinit var inputPassword : TextInputLayout
//    private lateinit var inputEmail : TextInputLayout
//    private lateinit var inputTanggalLahir : TextInputLayout
//    private lateinit var inputNomorTelepon : TextInputLayout
//    private lateinit var btnRegister : Button
//    private lateinit var btnCancel : Button
//    private lateinit var btn

    val db by lazy { ProfileDB(this) }
    private lateinit var binding : ActivityRegisterBinding

    private fun initComponent()
    {

//        inputUsername = findViewById(R.id.inputLayoutUsernameRegister)
//        inputPassword =  findViewById(R.id.inputLayoutPasswordRegister)
//        inputEmail = findViewById(R.id.inputLayoutEmailRegister)
//        inputTanggalLahir =  findViewById(R.id.inputLayoutTanggalLahir)
//        inputNomorTelepon = findViewById(R.id.inputLayoutTelp)
//        btnRegister = findViewById(R.id.btnRegisterAkun)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
//        initComponent()
        setTitle("Register Akun")
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val intent = Intent(this,LoginActivity::class.java)

        binding.btnRegisterAkun.setOnClickListener{
            var checkRegisterInput = false
            if (binding.inputUsername.text.toString().isEmpty())
            {
                binding.inputLayoutUsernameRegister.setError("Username Harus Diisi")
            }
            if(binding.inputPassword.text.toString().isEmpty())
            {
                binding.inputLayoutPasswordRegister.setError("Password Harus Diisi")
            }
            if(binding.inputEmail.text.toString().isEmpty())
            {
                binding.inputLayoutEmailRegister.setError("Email harus Diisi")
            }
            if(binding.inputTanggal.text.toString().isEmpty())
            {
                binding.inputLayoutTanggalLahir.setError("Tanggal Lahir Harus Diisi")
            }
            if(binding.inputTelp.text.toString().isEmpty())
            {
                binding.inputLayoutTelp.setError("Nomor Telepon Harus diisi")
            }

            if(binding.inputUsername.text!!.isNotEmpty() && binding.inputPassword.text!!.isNotEmpty() && binding.inputTelp.text!!.isNotEmpty()
                && binding.inputTanggal.text!!.isNotEmpty()){
                checkRegisterInput = true
            }
            if(!checkRegisterInput)
            {
                return@setOnClickListener
            }
            val bundle = Bundle()
            bundle.putString("username", binding.inputUsername.text.toString())
            bundle.putString("password", binding.inputPassword.text.toString())
            intent.putExtra("registerBundle", bundle)

            CoroutineScope(Dispatchers.IO).launch {
                db.profileDAO().addProfile(
                    Profile( binding.inputUsername.text.toString(),binding.inputPassword.text.toString(),
                    binding.inputEmail.text.toString(),binding.inputTanggal.text.toString(),binding.inputTelp.text.toString())
                )
                finish()
            }
            startActivity(intent)

        }

//        btnRegister.setOnClickListener {
//            var checkRegisterInput = false
//            val username : String = inputUsername.getEditText()?.getText().toString()
//            val password : String = inputPassword.getEditText()?.getText().toString()
//            val email : String = inputEmail.getEditText()?.getText().toString()
//            val tanggalLahir : String = inputTanggalLahir.getEditText()?.getText().toString()
//            val nomorTelp : String = inputNomorTelepon.getEditText()?.getText().toString()
//
//            //Cek masing-masing inputan & pastikan tidak kosong
//            if(username.isEmpty())
//            {
//                inputUsername.setError("Username Harus diisi")
//            }
//            if (password.isEmpty()){
//                inputPassword.setError("Password Harus diisi")
//            }
//            if (email.isEmpty()){
//                inputEmail.setError("Password Harus diisi")
//            }
//            if (tanggalLahir.isEmpty()){
//                inputTanggalLahir.setError("Password Harus diisi")
//            }
//            if (nomorTelp.isEmpty()){
//                inputNomorTelepon.setError("Password Harus diisi")
//            }
//            if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() && tanggalLahir.isNotEmpty() && nomorTelp.isNotEmpty()){
//                checkRegisterInput=true
//            }
//            if (!checkRegisterInput){
//                return@setOnClickListener
//            }
//            val intent = Intent(this, LoginActivity::class.java)
//            val mBundle : Bundle?= Bundle()
//            mBundle?.putString("username", username)
//            mBundle?.putString("password", password)
//            mBundle?.putString("email" , email)
//            mBundle?.putString("tanggalLahir", tanggalLahir)
//            mBundle?.putString("nomorTelp",nomorTelp)
//
//            if (mBundle != null) {
//                intent.putExtras(mBundle)
//            }
//            Toast.makeText(this,"Berhasil Mendaftarkan Akun",Toast.LENGTH_SHORT).show()
//            startActivity(intent)
//
//        }
    }
}