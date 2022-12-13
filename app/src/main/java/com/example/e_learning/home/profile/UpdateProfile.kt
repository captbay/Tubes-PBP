package com.example.e_learning.home.profile


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.e_learning.databinding.ActivityUpdateProfileBinding
import com.example.e_learning.home.HomeActivity
import com.example.e_learning.home.profile.dataprofile.Profile
import com.example.e_learning.zroomdatabase.ELEARNINGDB
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_update_profile.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import com.example.e_learning.R


class UpdateProfile : AppCompatActivity() {
    val db by lazy { ELEARNINGDB(this) }
    private lateinit var binding: ActivityUpdateProfileBinding
    private var queue: RequestQueue? = null
    private var layoutLoading: LinearLayout? = null

    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        val sp = getSharedPreferences("user", 0)
        val id : Int = sp.getInt("id", 0)
        Log.d("IDuser : " , id.toString())
        val view = binding.root
        setContentView(view)
        layoutLoading = findViewById(R.id.layout_loading)
        Log.d("Layoutnya : " , layoutLoading.toString())
        queue = Volley.newRequestQueue(this)

        val idLoginProfile : Int = sp.getInt("id",0)
        Log.d("idyanglogin", idLoginProfile.toString())
//        binding.linearLayout3.showLoading()
        setLoading(true)
        val StringRequest: StringRequest = object : StringRequest(Method.GET, ProfileApi.GET_BY_ID_URL + idLoginProfile,
            Response.Listener { response->
//                Log.d("Responss", response)
                val gson = Gson()
//              val profile = gson.fromJson(response, ResponseProfile::class.java)
                val profile = gson.fromJson(response, Array<Profile>::class.java)
//                Log.d("Profile", profile.toString())


                binding!!.editUsername.setText(profile[0].username)
                binding!!.editEmail.setText(profile[0].email)
                binding!!.editTglLahir.setText(profile[0].tglLahir)
                binding!!.editNoTelp.setText(profile[0].noTelp)

//                binding.linearLayout3.hideLoading()
                setLoading(false)
            }, Response.ErrorListener { error->
                setLoading(false)
//                binding.linearLayout3.hideLoading()
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(this,errors.getString("message"),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.WARNING,true).show();
                }catch (e: Exception){
                    FancyToast.makeText(this,e.message,
                        FancyToast.LENGTH_SHORT,
                        FancyToast.WARNING,true).show();
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
            var checkRegisterInput = false
            if (binding.editUsername.text.toString().isEmpty() || binding.editPassword.text.toString().isEmpty() || binding.editEmail.text.toString().isEmpty()
                || binding.editTglLahir.text.toString().isEmpty() || binding.editNoTelp.text.toString().isEmpty()
            ){
                FancyToast.makeText(this@UpdateProfile,"Data harus isi !",FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
            }else{
                setLoading(true)
                checkRegisterInput = true
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

                        if(mahasiswa != null){
                            FancyToast.makeText(this@UpdateProfile,"Data Berhasil Diupdate",FancyToast.LENGTH_SHORT, FancyToast.SUCCESS,false).show();
                        }

                        val returnIntent = Intent()//this@UpdateProfile, ProfileFragment::class.java
                        setResult(RESULT_OK, returnIntent)
                        finish()
                        setLoading(false)
                    }, Response.ErrorListener { error->
                        setLoading(false)
                        try{
                            val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                            val errors = JSONObject(responseBody)
                            FancyToast.makeText(this@UpdateProfile,errors.getString("message"),FancyToast.LENGTH_SHORT, FancyToast.WARNING,false).show();
                        }catch (e: Exception){
                            FancyToast.makeText(this@UpdateProfile,e.message,FancyToast.LENGTH_SHORT, FancyToast.WARNING,false).show();
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
            if(!checkRegisterInput)
            {
                return@setOnClickListener
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading?.visibility = View.VISIBLE
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading?.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val moveBack = Intent(this, HomeActivity::class.java).putExtra("back", "back")
        val mBundle = Bundle()
        mBundle.putString("back","back")
        moveBack.putExtra("back", mBundle)
        startActivity(moveBack)
    }
}