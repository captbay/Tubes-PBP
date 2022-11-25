package com.example.e_learning.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.e_learning.R
import com.example.e_learning.databinding.ActivityLoginBinding
import com.example.e_learning.home.HomeActivity
import com.example.e_learning.home.profile.ProfileApi
import com.example.e_learning.home.profile.dataprofile.Profile
import com.example.e_learning.home.profile.dataprofile.ResponseProfile
import com.example.e_learning.zroomdatabase.ELEARNINGDB
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class LoginActivity : AppCompatActivity() {
    //Atribut yang akan dipakai
    val db by lazy{ ELEARNINGDB(this) }


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
        getSupportActionBar()?.hide();
        //binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //Akhir dari binding
        getBundle()
        Logger.addLogAdapter(AndroidLogAdapter())
//        initComponents()

        //init button -> val ( kegunaan tidak pernah berubah)
        val btnClear = binding.btnClear
        val btnLogin = binding.btnLogin
        val btnRegister = binding.btnRegister
        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        queue = Volley.newRequestQueue(this@LoginActivity)

        val moveHome = Intent(this, HomeActivity::class.java)
        val inputUsername = view.findViewById<TextInputEditText>(R.id.loginUsername)
        val inputPassword = view.findViewById<TextInputEditText>(R.id.loginPassword)

        // Aksi btnClear ketika di klik
        btnClear.setOnClickListener OnClickListener@{
            //Untuk kebutuhan Debbugging
            Logger.d("Masuk ke btn clear listener")
            //Tombol Clear Hapus Text

            inputUsername.setText("")
            inputPassword.setText("")
            // Memunculkan SnackBar
            Snackbar.make(view, "Text Cleared Success", Snackbar.LENGTH_LONG).show()

        }

        btnLogin.setOnClickListener  OnClickListener@{
            Logger.d("Masuk ke btn login listener")
            //Inisialisasi kondisi
            var checkLogin = false // 0
            val tietLoginUsername = binding.loginUsername // tiet = TextInputEditText
            val tietLoginPassword = binding.loginPassword
            val username : String = tietLoginUsername.getText().toString()
            val password : String = tietLoginPassword.getText().toString()

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
                        tietLoginUsername.requestFocus()
                        tietLoginUsername.setError("username must be filled with text")
                        checkLogin = false
                        Logger.i("Test", "Pengecekan Username Kosong Sukses")
                    }else {
                        Logger.i("Test", "Username tidak kosong : "+username)
                        tietLoginUsername.setError(null)
                    }

                    //Pengecekan apakah Inputan Password kosong
                    if(password.isEmpty()) {
                        tietLoginPassword.setError("password must be filled with text")
                        Snackbar.make(binding.loginLayout,"Passwordnya kosong boss",Snackbar.LENGTH_SHORT).show()
                        checkLogin = false
                        Log.i("Test","Pengecekan Password Kosong Sukses ")
                    }else{
                        Log.i("Test", "Password Tidak Kosong : "+password)
                        tietLoginPassword.setError(null)
                    }
                }

//                //sementara loss aja dulu
//                checkLogin=true

//                withContext(Dispatchers.Main)
//                {
//                    if (checkLogin == true) {
//                        startActivity(moveHome)
//                    }else if(checkLogin == false){
//                        Snackbar.make(view, "Username/password salah", Snackbar.LENGTH_LONG).show()
//                    }
//                    else
//                    {
//                        //Pengecekan apakah inputan username kosong
//                        if(username.isEmpty()) {
//                            tietLoginUsername.requestFocus()
//                            tietLoginPassword.setError("username must be filled with text")
//                            checkLogin = false
//                            Log.i("Test", "Pengecekan Username Kosong Sukses")
//                        }else {
//                            Log.i("Test", "Username tidak kosong : "+username)
//                            tietLoginUsername.setError(null)
//                        }
//
//                        //Pengecekan apakah Inputan Password kosong
//                        if(password.isEmpty()) {
//                            tietLoginPassword.setError("password must be filled with text")
//                            Snackbar.make(view,"Passwordnya kosong boss",Snackbar.LENGTH_SHORT).show()
//                            checkLogin = false
//                            Log.i("Test","Pengecekan Password Kosong Sukses ")
//                        }else{
//                            Log.i("Test", "Password Tidak Kosong : "+password)
//                            tietLoginPassword.setError(null)
//                        }
//                    }
//                }
            }

        }

        //btnRegister untuk pindah ke ActivityRegister
        btnRegister.setOnClickListener {
            binding.loginPassword.setError("")
            val moveRegister = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(moveRegister)
        }
    }

    private fun login(){
        Logger.d("Masuk ke fungsi login listener")
        val inputUsername = findViewById<TextInputEditText>(R.id.loginUsername)
        val inputPassword = findViewById<TextInputEditText>(R.id.loginPassword)
        val profile = Profile(
            0,
            inputUsername.getText().toString(),
            inputPassword.getText().toString(),
            "","",""
        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.POST, ProfileApi.LOGIN_URL, Response.Listener { response ->
                val gson = Gson()
                Logger.d("volleyerr",response.toString())
                val mahasiswa = gson.fromJson(response, ResponseProfile::class.java)

                if(mahasiswa != null)
                    FancyToast.makeText(this,"Berhasil Login!",FancyToast.LENGTH_LONG, FancyToast.SUCCESS,true).show();

                val sp = this@LoginActivity.getSharedPreferences("user", 0)
                val editor = sp.edit()
                editor.putInt("id", mahasiswa.data.id)
                editor.commit()

                val moveHome = Intent(this, HomeActivity::class.java)
                this?.startActivity(moveHome)
                this?.finish()


            }, Response.ErrorListener { error ->
                Logger.d("volleyerr",error.toString())
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(this@LoginActivity,errors.getString("message"),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    Logger.d("volleyerr",errors.getString("message"))
                }
                catch (e:Exception){
                    FancyToast.makeText(this@LoginActivity,e.message,FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    Logger.d("volleyerr",e.message.toString())
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

//    fun initComponents()
//    {
//        //overridePendingTransition(0,1) --> niatannya mau buat reset condition
//        //Hubungkan variabel dengan view di layout
//        //Ubah Title pada AppBar Aplikasi
//        inputUsername = findViewById(R.id.inputLayoutUsername)
//        inputPassword = findViewById(R.id.inputLayoutPassword)
//        mainLayout = findViewById(R.id.loginLayout)
//        getSupportActionBar()?.hide();
//        val mBundle = intent.extras
//        if(mBundle!=null)
//        {
//            inputUsername.getEditText()?.setText(mbUsername)
////            inputPassword.getEditText()?.setText(mbPassword)
//        }
//
//    }
}