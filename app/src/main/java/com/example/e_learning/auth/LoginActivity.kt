package com.example.e_learning.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.e_learning.home.HomeActivity
import com.example.e_learning.home.profile.ProfileApi
import com.example.e_learning.R
import com.example.e_learning.zroomdatabase.ELEARNINGDB
import com.example.e_learning.home.profile.dataprofile.Profile
import com.example.e_learning.home.profile.dataprofile.ResponseProfile
import com.example.e_learning.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.nio.charset.StandardCharsets

//BELOM JADI

class LoginActivity : AppCompatActivity() {
    //Atribut yang akan dipakai
    val db by lazy{ ELEARNINGDB(this) }


    private lateinit var inputUsername : TextInputLayout
    private lateinit var inputPassword : TextInputLayout
    private lateinit var mainLayout : ConstraintLayout
    var mbUsername : String? = null
    var mbPassword: String? = null
    var sharedPreferences  : SharedPreferences? = null
    private val myPreference = "myPref"
    private val id = "idKey"

    private lateinit var binding : ActivityLoginBinding
    private var queue: RequestQueue? = null

    //Tahap awal siklus hidup aplikasi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //splashscreen
        Thread.sleep(2000)

        val splashScreen = installSplashScreen()

        //binding
//        binding = ActivityLoginBinding.inflate(layoutInflater)
//        val view = binding.root
        setContentView(R.layout.activity_login)
        getBundle()
//        initComponents()
        //Init Button --> bertipe val karena kegunaannya tidak berubah
        val btnClear : Button = findViewById(R.id.btnClear)
        val btnLogin : Button = findViewById(R.id.btnLogin)
        val btnRegister : Button = findViewById(R.id.btnRegister)
        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        queue = Volley.newRequestQueue(this@LoginActivity)

        val moveHome = Intent(this, HomeActivity::class.java)

        // Aksi btnClear ketika di klik
        binding.btnClear.setOnClickListener{
            binding.loginUsername.setText("")
//            inputUsername.getEditText()?.setText("")
//            inputPassword.getEditText()?.setText("")

            // Memunculkan SnackBar
            Snackbar.make(mainLayout, "Text Cleared Success", Snackbar.LENGTH_LONG).show()
        }

        btnLogin.setOnClickListener  OnClickListener@{

            //Inisialisasi kondisi
            var checkLogin = false // 0
            val username : String = inputUsername.getEditText()?.getText().toString()
            val password : String = inputPassword.getEditText()?.getText().toString()

            CoroutineScope(Dispatchers.IO).launch {
                val Profile = db.profileDAO().getProfile()

                for (i in Profile) {
                    if (username == i.username && password == i.password) {
                        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                        editor.putString(id, i.id.toString())
                        editor.apply()
//                        checkLogin = true
                         login()
                        break
                    }
                }

                try{
                    login()
                }
                catch(e : Error){
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
                }

                //sementara loss aja dulu
                checkLogin=true

                withContext(Dispatchers.Main)
                {
                    if (checkLogin == true) {
                        startActivity(moveHome)
                    }else if(checkLogin == false){
                        Snackbar.make(mainLayout, "Username/password salah", Snackbar.LENGTH_LONG).show()
                    }
                    else
                    {
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
                    }
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

    private fun login(){
        binding.btnLogin.id
        val profile = Profile(
            0,
            binding.inputLayoutUsername?.getEditText()?.getText().toString(),
            binding.inputLayoutPassword?.getEditText()?.getText().toString(),
            "","",""
        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.POST, ProfileApi.LOGIN_URL, Response.Listener { response ->
                val gson = Gson()
                Log.d("volleyerr",response.toString())
                val mahasiswa = gson.fromJson(response, ResponseProfile::class.java)

                if(mahasiswa != null)
                    Toast.makeText(this@LoginActivity, "Berhasil Login", Toast.LENGTH_SHORT).show()

                val sp = this@LoginActivity.getSharedPreferences("user", 0)
                val editor = sp.edit()
                editor.putInt("id", mahasiswa.data.id)
                editor.commit()

                val moveHome = Intent(this, HomeActivity::class.java)
                this?.startActivity(moveHome)
                this?.finish()

                binding.btnLogin
            }, Response.ErrorListener { error ->
                binding.btnLogin
                Log.d("volleyerr",error.toString())
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(this@LoginActivity, errors.getString("message"), Toast.LENGTH_SHORT).show()
                    Log.d("volleyerr",errors.getString("message"))
                }
                catch (e:Exception){
                    Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
                    Log.d("volleyerr",e.message.toString())
                }
            }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>{
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray{
                val gson = Gson()
                val requestBody = gson.toJson(profile)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)
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