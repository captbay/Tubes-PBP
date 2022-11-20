package com.example.e_learning.Fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.e_learning.Adapter.KelasAdapter
import com.example.e_learning.Api.KelasApi
import com.example.e_learning.R
import com.example.e_learning.models.Kelas
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentKelas.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentKelas : Fragment() {
    private var adapter: KelasAdapter? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null
    var mActivity: Activity? = this.activity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kelas, container, false)
    }

    companion object{
        const val LAUNCH_ADD_ACTIVITY = 123
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        queue = Volley.newRequestQueue(mActivity)
        val rvKelas : RecyclerView = view.findViewById(R.id.rv_kelas)

//        rvKelas.layoutManager = layoutManager
//        rvKelas.setHasFixedSize(true)
//        rvKelas.adapter = adapter
        adapter = mActivity?.let { KelasAdapter(ArrayList(), it) }
        rvKelas.layoutManager = layoutManager
        rvKelas.setHasFixedSize(true)
        rvKelas.adapter = adapter
        allKelas()
    }

    private fun allKelas(){

        val stringRequest : StringRequest = object:
            StringRequest(Method.GET, KelasApi.GET_ALL_URL, Response.Listener { response ->
                val gson = Gson()
                var kelas : Array<Kelas> = gson.fromJson(response, Array<Kelas>::class.java)

                adapter!!.setkelasList(kelas)



                if(!kelas.isEmpty())
                    Toast.makeText(mActivity, "Data berhasil diambil", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(mActivity, "Data Kosong!", Toast.LENGTH_SHORT).show()

            }, Response.ErrorListener { error ->
                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(mActivity, errors.getString("message"), Toast.LENGTH_SHORT).show()
                } catch (e: Exception){
                    Toast.makeText(mActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }

        }
        queue!!.add(stringRequest)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LAUNCH_ADD_ACTIVITY){
            if(resultCode == Activity.RESULT_OK){
                allKelas()
            }
        }
    }

}