package com.example.e_learning.home.tugas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.e_learning.R
import com.example.e_learning.databinding.FragmentTugasBinding
import com.example.e_learning.home.tugas.data.Tugas
import com.example.e_learning.home.tugas.data.TugasAdapter

class TugasFragment : Fragment() {
    private var _binding : FragmentTugasBinding? = null
    private val binding get() = _binding!!
    private var adapter: TugasAdapter? = null
    private var queue: RequestQueue? = null
    private var layoutLoading: LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tugas, container, false)
    }

    companion object{
        const val LAUNCH_ADD_ACTIVITY = 123
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val adapter : TugasAdapter = TugasAdapter(Tugas.litsOfTugas)

        val rvTugas : RecyclerView = view.findViewById(R.id.rv_tugas)

        rvTugas.layoutManager = layoutManager

        rvTugas.setHasFixedSize(true)

        rvTugas.adapter = adapter
    }

}