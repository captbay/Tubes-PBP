package com.example.e_learning

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.e_learning.data.ELEARNINGDB
import com.example.e_learning.databinding.ActivityUpdateProfileBinding
import com.example.e_learning.data.profile.Profile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateProfile : AppCompatActivity() {
    val db by lazy { ELEARNINGDB(this) }
    private lateinit var binding: ActivityUpdateProfileBinding
    private val id = "idKey"
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val id = sharedPreferences!!.getString(id,"")!!.toInt()
        loadData(id)

        binding.buttonUpdate.setOnClickListener { menuItem->
            when(menuItem.id){
                R.id.button_update -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        val Profile = db?.profileDAO()?.updateProfile(
                            Profile(id,binding.editUsername.text.toString()
                            ,binding.editPassword.text.toString(),binding.editEmail.text.toString(),
                            binding.editTglLahir.text.toString(),binding.editNoTelp.text.toString())
                        )
                    }
                    finish()
                    val intent = Intent(this, HomeActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("key","iniTerisi")
                    intent.putExtra("keyBundle",bundle)
                    startActivity(intent)

                    true
                }
                else -> false
            }

        }




    }

    fun loadData(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val Profile = db?.profileDAO()?.getProfile(id)?.get(0)


            withContext(Dispatchers.Main){
                binding.editUsername.setText(Profile?.username)
                binding.editEmail.setText(Profile?.email)
                binding.editNoTelp.setText(Profile?.noTelp)
                binding.editTglLahir.setText(Profile?.tglLahir)
                binding.editPassword.setText(Profile?.password)
            }

        }
    }
}