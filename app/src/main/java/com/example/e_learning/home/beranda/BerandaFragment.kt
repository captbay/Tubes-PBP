package com.example.e_learning.home.beranda


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.e_learning.R
import com.example.e_learning.home.beranda.maps.MapsActivity
import com.example.e_learning.databinding.FragmentBerandaBinding
import com.example.e_learning.home.beranda.todoList.ToDoFragment
import com.example.e_learning.home.kelas.KelasFragment
import com.example.e_learning.home.kelas.data.Kelas
import com.example.e_learning.home.kelas.data.KelasAdapter
import com.example.e_learning.home.kelas.data.KelasApi
import com.example.e_learning.home.kelas.data.ResponseKelas
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import java.nio.charset.StandardCharsets


//import kotlinx.android.synthetic.main.fragment_beranda.*


class BerandaFragment : Fragment() {
    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!
    private var adapter: KelasAdapter? = null
    private var queue: RequestQueue? = null
    private var layoutLoading: LinearLayout? = null

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
        layoutLoading = requireView().findViewById(R.id.layout_loading)
        queue = Volley.newRequestQueue(requireActivity())
        binding.srTodo.setOnRefreshListener ( SwipeRefreshLayout.OnRefreshListener{ allKelas() })

        adapter = KelasAdapter(ArrayList(),requireContext() , this@BerandaFragment)
        binding.rvTodo.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTodo.adapter = adapter
        allKelas()
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


    private fun allKelas(){
        binding.srTodo!!.isRefreshing = true
        val stringRequest : StringRequest = object:
            StringRequest(Request.Method.GET, KelasApi.GET_ALL_URL, Response.Listener { response ->
                Log.d("responsee", response)
                val gson = Gson()
//                var kelas : Array<Kelas> = gson.fromJson(response, Array<Kelas>::class.java)
                var kelas : Array<Kelas> = gson.fromJson(response, ResponseKelas::class.java).data.toTypedArray()

                adapter!!.setkelasList(kelas)
                binding.srTodo!!.isRefreshing = false

                checkIfFragmentAttached {
                    if(!kelas.isEmpty())
                        FancyToast.makeText(requireContext(),"Data Berhasil Diambil!", FancyToast.LENGTH_SHORT,
                            FancyToast.SUCCESS,false).show()
                    else
                        FancyToast.makeText(requireContext(),"Data Kosong!",
                            FancyToast.LENGTH_SHORT,
                            FancyToast.WARNING,false).show()
                }


            }, Response.ErrorListener { error ->
                Log.d("responseror", error.toString())
                binding.srTodo!!.isRefreshing = false
                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(requireContext(),errors.getString("message"),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.WARNING,false).show()
                } catch (e: Exception){
                    FancyToast.makeText(requireContext(),e.message,
                        FancyToast.LENGTH_SHORT,
                        FancyToast.WARNING,false).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }

        }
        Log.d("Queue Test", queue.toString())
        Log.d("Link Api", queue.toString())
        queue!!.add(stringRequest)
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading!!.visibility = View.VISIBLE
        } else {
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading!!.visibility = View.GONE
        }
    }

    fun checkIfFragmentAttached(operation: Context.() -> Unit) {
        if (isAdded && context != null) {
            operation(requireContext())
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == KelasFragment.LAUNCH_ADD_ACTIVITY){
            if(resultCode == Activity.RESULT_OK){
                allKelas()
            }
        }
    }
}

