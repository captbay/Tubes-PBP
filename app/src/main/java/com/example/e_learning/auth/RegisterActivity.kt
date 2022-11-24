package com.example.e_learning.auth

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.e_learning.home.HomeActivity
import com.example.e_learning.home.profile.ProfileApi
import com.example.e_learning.R
import com.example.e_learning.zroomdatabase.ELEARNINGDB
import com.example.e_learning.databinding.ActivityRegisterBinding
import com.example.e_learning.home.profile.dataprofile.Profile
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class RegisterActivity : AppCompatActivity() {
    val db by lazy { ELEARNINGDB(this) }
    private lateinit var binding : ActivityRegisterBinding
    private val channel_id ="channel_notification_01"
    private val notificationId1 =101
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide();
        queue = Volley.newRequestQueue(this@RegisterActivity)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        createNotificationChannel()
        val moveLogin = Intent(this, LoginActivity::class.java)
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
                val bundle = Bundle()
                bundle.putString("username", binding.regisUsername.text.toString())
                bundle.putString("password", binding.regisPass.text.toString())
                moveLogin.putExtra("registerBundle", bundle)

                register()
                sendNotification1()
                startActivity(moveLogin)

            }
            if(!checkRegisterInput)
            {
                return@setOnClickListener
            }

        }
    }

    private fun register(){
        val inputUsername = findViewById<TextInputEditText>(R.id.regisUsername)
        val inputPassword = findViewById<TextInputEditText>(R.id.regisPass)
        val inputEmail = findViewById<TextInputEditText>(R.id.regisEmail)
        val inputTgl = findViewById<TextInputEditText>(R.id.regisTgl)
        val inputTelp = findViewById<TextInputEditText>(R.id.regisTelp)
            setLoading(true)
            val mahasiswa = Profile(
                0,
                inputUsername.getText().toString(),
                inputPassword.getText().toString(),
                inputEmail.getText().toString(),
                inputTgl.getText().toString(),
                inputTelp.getText().toString(),
            )

            val StringRequest: StringRequest = object : StringRequest(Method.POST, ProfileApi.REGISTER_URL,
                Response.Listener { response ->
                    val gson = Gson()
                    val Profile = gson.fromJson(response, Profile::class.java)

                    if(Profile != null)
                        Toast.makeText(this@RegisterActivity,"Berhasil Register", Toast.LENGTH_SHORT).show()

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
                            this@RegisterActivity,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }catch (e: Exception){
                        Toast.makeText(this@RegisterActivity,e.message, Toast.LENGTH_SHORT).show()
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
                    val requestBody = gson.toJson(mahasiswa)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }
            queue!!.add(StringRequest)

    }

    private fun createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val name ="Register Notification"
            val descriptionText = "Success Register"

            val channel1 = NotificationChannel(channel_id, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }

            val notificationManager : NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
        }
    }

    private fun sendNotification1(){
        //Notif di klik kembali ke Login Activity
        val intent : Intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent : PendingIntent = PendingIntent.getActivity(this,0,intent,0)
        val broadcastIntent : Intent = Intent(this, HomeActivity::class.java)
        val actionIntent = PendingIntent.getActivity(this,0,broadcastIntent,0)
        val bitmap  = BitmapFactory.decodeResource(resources, R.drawable.welcome_notif2)
        val builder = NotificationCompat.Builder(this, channel_id)
            .setSmallIcon(R.drawable.ic_baseline_account_circle_24)
            .setContentTitle("Welcome " +binding?.regisUsername?.text.toString() +"!! 😊😊")
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.YELLOW)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "Login Langsung ?? ", actionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))


        with(NotificationManagerCompat.from(this)){
            notify(notificationId1,builder.build())
        }
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

