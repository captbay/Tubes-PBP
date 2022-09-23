package com.example.e_learning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.e_learning.databinding.ActivityRegisterBinding
import com.example.e_learning.entity.Profile
import com.example.e_learning.entity.ProfileDB
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
        val db by lazy {ProfileDB(this)}
        private lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setTitle("Register Akun")
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val moveLogin = Intent(this,LoginActivity::class.java)
//      initComponent()

        binding.btnRegisterAkun.setOnClickListener{
            var checkRegisterInput = false
            if (binding.regisUsername.text.toString().isEmpty())
            {
                binding.inputLayoutUsernameRegister.setError("Username Harus Diisi")
            }
            if(binding.regisPass.text.toString().isEmpty())
            {
                binding.inputLayoutPasswordRegister.setError("Password Harus Diisi")
            }
            if(binding.regisEmail.text.toString().isEmpty())
            {
                binding.inputLayoutEmailRegister.setError("Email harus Diisi")
            }
            if(binding.regisTgl.text.toString().isEmpty())
            {
                binding.inputLayoutTanggalLahir.setError("Tanggal Lahir Harus Diisi")
            }
            if(binding.regisTelp.text.toString().isEmpty())
            {
                binding.inputLayoutTelp.setError("Nomor Telepon Harus diisi")
            }

            if(binding.regisUsername.text!!.isNotEmpty() && binding.regisPass.text!!.isNotEmpty() && binding.regisTelp.text!!.isNotEmpty()
                && binding.regisTgl.text!!.isNotEmpty()){
                checkRegisterInput = true
            }
            if(!checkRegisterInput)
            {
                return@setOnClickListener
            }
            val bundle = Bundle()
            bundle.putString("username", binding.regisUsername.text.toString())
            bundle.putString("password", binding.regisPass.text.toString())
            moveLogin.putExtra("registerBundle", bundle)

            CoroutineScope(Dispatchers.IO).launch {
                db.profileDAO().addProfile(
                    Profile( binding.regisUsername.text.toString(),binding.regisPass.text.toString(),
                        binding.regisEmail.text.toString(),binding.regisTgl.text.toString(),binding.regisTelp.text.toString())
                )
                finish()
            }
            startActivity(moveLogin)
        }



        }
    }
