package com.example.e_learning

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.e_learning.databinding.FragmentProfileBinding
import com.example.e_learning.entity.ProfileDB
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    val db by lazy { activity?.let { ProfileDB(it) } }
    private val id = "idKey"
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences? = null
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val LogoutButton: FloatingActionButton = view.findViewById(R.id.floatingActionLogout)
        val logout = Intent(this.getActivity(), LoginActivity::class.java)

        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)

        CoroutineScope(Dispatchers.IO).launch {
            val profile = db?.profileDAO()?.getProfile(sharedPreferences!!.getString(id,"")!!.toInt())?.get(0)
            binding.username.setText(profile?.username)
            binding.password.setText(profile?.password)
            binding.email.setText(profile?.email)
            binding.noTelp.setText(profile?.noTelp)
            binding.tglLahir.setText(profile?.tglLahir)

            binding.floatingActionUpdate.setOnClickListener {
                val moveEdit = Intent(activity, UpdateProfile::class.java)
                startActivity(moveEdit)
                activity?.finish()
            }
        }

        LogoutButton.setOnClickListener(){
            val builder1 = AlertDialog.Builder(
                this.requireContext()
            )
            builder1.setMessage("Anda Yakin Ingin Meninggal Aplikasi ini ? ")
            builder1.setCancelable(true)

            builder1.setPositiveButton(
                "Yes"
            ) { dialog, id -> startActivity(logout) }

            builder1.setNegativeButton(
                "No"
            ) { dialog, id -> dialog.cancel() }

            val alert11 = builder1.create()
            alert11.show()
        }



    }

}

