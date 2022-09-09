package com.example.e_learning

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val LogoutButton: FloatingActionButton = view.findViewById(R.id.floatingActionLogout)
        val logout = Intent(this.getActivity(), LoginActivity::class.java)

//        LogoutButton.setOnClickListener {
//            val alertDialog : AlertDialog = AlertDialog.Builder(this.requireActivity()!!.getApplicationContext()).create()
//            alertDialog.setTitle("Log out Dialog")
//            alertDialog.setMessage("Do You Want Log Out??")
//
////            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES")
////            {
////                dialog, which -> startActivity(logout)
////                dialog.dismiss()
////            }
////            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO")
////            {
////                dialog, which ->
////                dialog.dismiss()
////            }
//            alertDialog.show()
//        }
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

