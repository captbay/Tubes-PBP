package com.example.e_learning

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
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
        private val channel_id ="channel_notification_01"
        private val notificationId1 =101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setTitle("Register Akun")
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        createNotificationChannel()
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
                    Profile( 0,binding.regisUsername.text.toString(),binding.regisPass.text.toString(),
                        binding.regisEmail.text.toString(),binding.regisTgl.text.toString(),binding.regisTelp.text.toString())
                )
                finish()
            }
            sendNotification1()
            startActivity(moveLogin)
        }



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
        val broadcastIntent : Intent = Intent(this,HomeActivity::class.java)
        val actionIntent = PendingIntent.getActivity(this,0,broadcastIntent,0)
        val bitmap  = BitmapFactory.decodeResource(resources,R.drawable.welcome_notif2)
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
}

