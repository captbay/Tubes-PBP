package com.example.e_learning

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val LogoutButton : FloatingActionButton = view.findViewById(R.id.floatingActionLogout)

        LogoutButton.setOnClickListener {
//            val alertDialog : AlertDialog = AlertDialog.Builder(context
//            alertDialog.setTitle("Log out Dialog")
//            alertDialog.setMessage("Do You Want Log Out??")

//            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES")
//            {
//            }
//            alertDialog.setButton(AlertDialog, DialogInterface.BUTTON_POSITIVE, "NO")
//            {
//                dialog, which -> dialog.dismiss()
//            }
//            alertDialog.show()
        }

    }

}
