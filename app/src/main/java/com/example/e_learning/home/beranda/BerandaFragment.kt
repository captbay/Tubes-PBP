package com.example.e_learning.home.beranda


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e_learning.R
import com.example.e_learning.home.beranda.maps.MapsActivity
import com.example.e_learning.databinding.FragmentBerandaBinding


//import kotlinx.android.synthetic.main.fragment_beranda.*


class BerandaFragment : Fragment() {
    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    //Test
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonCreate.setOnClickListener {
                getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_fragment, ToDoFragment())
                    .addToBackStack(null)
                    .commit()

        }

        binding.mapIcon.setOnClickListener {
            val moveMaps = Intent(activity, MapsActivity::class.java)
            startActivity(moveMaps)
        }
    }

}

