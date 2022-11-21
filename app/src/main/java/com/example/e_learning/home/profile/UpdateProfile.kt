package com.example.e_learning.home.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.e_learning.zroomdatabase.ELEARNINGDB
import com.example.e_learning.databinding.ActivityUpdateProfileBinding
import com.example.e_learning.home.profile.dataprofile.Profile
import kotlinx.android.synthetic.main.activity_update_profile.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import com.google.gson.Gson

class UpdateProfile : AppCompatActivity() {
    val db by lazy { ELEARNINGDB(this) }
    private lateinit var binding: ActivityUpdateProfileBinding
    private var queue: RequestQueue? = null

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
//        loadData(id)
        queue = Volley.newRequestQueue(this)

        setLoading(true)
        val StringRequest: StringRequest = object : StringRequest(Method.GET, ProfileApi.GET_BY_ID_URL + id,
            Response.Listener { response->
                val gson = Gson()
                val Profile = gson.fromJson(response, Profile::class.java)

                binding!!.editUsername.setText(Profile?.username)
                binding!!.editEmail.setText(Profile?.email)
                binding!!.editTglLahir.setText(Profile?.tglLahir)
                binding!!.editNoTelp.setText(Profile?.noTelp)

                Toast.makeText(this,"Data Berhasil Diambil!", Toast.LENGTH_SHORT).show()
                setLoading(false)
            }, Response.ErrorListener { error->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@UpdateProfile,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                }catch (e: Exception){
                    Toast.makeText(this@UpdateProfile,e.message, Toast.LENGTH_SHORT).show()
                }
            }
        ){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String,String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(StringRequest)

        binding.buttonUpdate.setOnClickListener {
            setLoading(true)

            val profile = Profile(
                id,
                binding!!.editUsername.text.toString(),
                binding!!.editPassword.text.toString(),
                binding!!.editEmail.text.toString(),
                binding!!.editTglLahir.text.toString(),
                binding!!.editNoTelp.text.toString(),
            )
            val StringRequest: StringRequest = object : StringRequest(Method.PUT, ProfileApi.UPDATE_URL + id,
                Response.Listener { response ->
                    val gson = Gson()
                    val mahasiswa = gson.fromJson(response, Profile::class.java)

                    if(mahasiswa != null)
                        Toast.makeText(this@UpdateProfile,"Data Berhasil Diupdate", Toast.LENGTH_SHORT).show()

                    val returnIntent = Intent()
                    setResult(RESULT_OK, returnIntent)
                    finish()

                    setLoading(false)
                }, Response.ErrorListener { error->
                    setLoading(false)
                    try{
                        val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this@UpdateProfile,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }catch (e: Exception){
                        Toast.makeText(this@UpdateProfile,e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            ){
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String,String>()
                    headers["Accept"] = "application/json"
                    return headers
                }
                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray {
                    val gson = Gson()
                    val requestBody = gson.toJson(profile)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }
            queue!!.add(StringRequest)
        }
        setContentView(binding?.root)
    }

    fun setLoading(isLoading:Boolean){
        if(isLoading){
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            binding.layoutLoading?.root?.visibility = View.VISIBLE
        }else{
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            binding.layoutLoading?.root?.visibility = View.VISIBLE
        }
    }
}