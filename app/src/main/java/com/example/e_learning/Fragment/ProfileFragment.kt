package com.example.e_learning.Fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.e_learning.Activity.CameraActivity
import com.example.e_learning.Activity.LoginActivity
import com.example.e_learning.Activity.UpdateProfile
import com.example.e_learning.Api.ProfileApi
import com.example.e_learning.R
import com.example.e_learning.data.ELEARNINGDB
import com.example.e_learning.data.profile.ResponseProfile
import com.example.e_learning.databinding.FragmentProfileBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_profile.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class ProfileFragment : Fragment() {
    val db by lazy { activity?.let { ELEARNINGDB(it) } }
    private val id = "idKey"
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences? = null
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val channelLogout = "channelLogoutNotification"
    private val notificationId = 10
    private var queue: RequestQueue? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        createNotificationChannel()
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val LogoutButton: FloatingActionButton = view.findViewById(R.id.floatingActionLogout)
        val logout = Intent(this.getActivity(), LoginActivity::class.java)
        val camera = button
        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        queue = Volley.newRequestQueue(requireContext())

        camera.setOnClickListener{
            val moveCamera = Intent(activity,CameraActivity::class.java)
            startActivity(moveCamera)
        }

        allProfile()

//        CoroutineScope(Dispatchers.IO).launch {
//            val profile = db?.profileDAO()?.getProfile(sharedPreferences!!.getString(id,"")!!.toInt())?.get(0)
//            binding.username.setText(profile?.username)
//            binding.password.setText(profile?.password)
//            binding.email.setText(profile?.email)
//            binding.noTelp.setText(profile?.noTelp)
//            binding.tglLahir.setText(profile?.tglLahir)
//
            binding.floatingActionUpdate.setOnClickListener {
                val moveEdit = Intent(activity, UpdateProfile::class.java)
                startActivity(moveEdit)
                activity?.finish()
            }
//        }

        LogoutButton.setOnClickListener(){
            val builder1 = AlertDialog.Builder(
                this.requireContext()
            )
            builder1.setMessage("Anda Yakin Ingin Meninggal Aplikasi ini ? ")
            builder1.setCancelable(true)

            builder1.setPositiveButton(
                "Yes"
            ) { dialog, id -> startActivity(logout)
            sendNotification()}

            builder1.setNegativeButton(
                "No"
            ) { dialog, id -> dialog.cancel() }

            val alert11 = builder1.create()
            alert11.show()


        }
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val logoutNotif = NotificationChannel(channelLogout, name,  NotificationManager.IMPORTANCE_DEFAULT).apply{
                description = descriptionText
            }

            val notificationManager: NotificationManager =  requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(logoutNotif)
        }
    }

    private fun  sendNotification(){
        val builder = NotificationCompat.Builder(requireContext(), channelLogout)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle("Berhasil Logout!!")
            .setContentText("Sampai Jumpa Lagi")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Thor: Love and Thunder menceritakan Thor dalam sebuah perjalanan yang belum pernah ia jalani – pencariankedamaian batin. Namun, masa pensiunnya terganggu oleh seorang pembunuh galaksi yang dikenal sebagai Gorr sang Dewa Jagal , yang ingin memusnahkan para dewa.")
                    .setSummaryText("Pembelajaran Hari Ini Sudah Selesai")

            )

        with(NotificationManagerCompat.from(requireContext())){
            notify(notificationId, builder.build())
        }
    }

    private fun allProfile(){
//        binding.linearLayout3.showLoading()
        val StringRequest: StringRequest = object : StringRequest(Method.GET, ProfileApi.GET_BY_ID_URL + id,
            Response.Listener { response->
                val gson = Gson()
                val profile = gson.fromJson(response, ResponseProfile::class.java)

                binding!!.username.setText(profile.data.username)
                binding!!.email.setText(profile.data.email)
                binding!!.tglLahir.setText(profile.data.tglLahir)
                binding!!.noTelp.setText(profile.data.tglLahir)

                Toast.makeText(requireActivity(),"Data Berhasil Diambil!", Toast.LENGTH_SHORT).show()
//                binding.linearLayout3.hideLoading()
            }, Response.ErrorListener { error->
//                binding.linearLayout3.hideLoading()
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        requireActivity(),
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                }catch (e: Exception){
                    Toast.makeText(requireActivity(),e.message, Toast.LENGTH_SHORT).show()
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
    }
}

