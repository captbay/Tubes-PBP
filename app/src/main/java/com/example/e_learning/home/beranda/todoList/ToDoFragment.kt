package com.example.e_learning.home.beranda.todoList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SearchView
import android.widget.Toast
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.e_learning.R
import com.example.e_learning.databinding.FragmentTodoBinding
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class ToDoFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private var adapter: TodoAdapter? = null
    private var queue: RequestQueue? = null
    private var layoutLoading: LinearLayout? = null

    companion object {
        val LAUNCH_ADD_ACTIVITY = 123
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutLoading = requireView().findViewById(R.id.layout_loading)
        queue = Volley.newRequestQueue(requireActivity())
        binding.srTodo.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { allTodo() })
        binding.svTodo.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(s: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(s: String?): Boolean {
                adapter!!.filter.filter(s)
                return false
            }
        })

        binding.fabAdd.setOnClickListener {
            val i = Intent(requireActivity(), AddEditActivity::class.java)
            startActivityForResult(i, LAUNCH_ADD_ACTIVITY)
        }
        adapter = TodoAdapter(ArrayList(), requireContext(), this@ToDoFragment)
        binding.rvTodo.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTodo.adapter = adapter
        allTodo()
    }

    private fun allTodo() {
        binding.srTodo!!.isRefreshing = true
        //val url = "https://elearning-pbp.herokuapp.com/todolists"
        val sharedPreferences = requireContext().getSharedPreferences("user", 0)
        var user_id = sharedPreferences.getInt("id", 0)
        val stringRequest: StringRequest = object :
            StringRequest(Request.Method.GET, TodoApi.GET_ALL_URL, Response.Listener { response ->
                Log.d("responsee", response)
                val gson = Gson()
                var todo: Array<ToDoList> = gson.fromJson(response, ResponseData::class.java).data.toTypedArray()
                    todo = todo.filter { td -> td.user_id ==  user_id}.toTypedArray()
                    Log.d("ini todo" , todo.toString())
//                filter {
//                        doList -> doList.user_id == user_id
//                }.toTypedArray()
//                Log.d("todonyawoiii", todo[0].judul)
//                Log.d("gson", gson.toString())
//                Log.d("ArrayHasil", todo.toString())
//                Log.d("todo", todo[0].judul)
//                Log.d("responsenya", response.toString())
                adapter!!.setTodoList(todo)
                adapter!!.filter.filter(binding.svTodo!!.query)
                binding.srTodo!!.isRefreshing = false

                if (!todo.isEmpty())
                    FancyToast.makeText(requireContext(),"Data Berhasil Diambil!",
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,false).show()
                else
                    FancyToast.makeText(requireContext(),"Data Kosong!",FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show()
                setLoading(false)
            }, Response.ErrorListener { error ->
                Log.d("responseror", error.toString())
                binding.srTodo!!.isRefreshing = false
                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(requireContext(),errors.getString("message"),FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show()
                } catch (e: Exception) {
                    FancyToast.makeText(requireContext(),e.message,FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show()
                }
            }) {
            // Menambahkan header pada request
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }

//        Log.d("Hasil Queue",queue.toString())
        Log.d("Queue Test", queue.toString())
        Log.d("Link Api", queue.toString())
        queue!!.add(stringRequest)
    }

    public fun deleteTodo(id: Long) {
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.DELETE, TodoApi.DELETE_URL + id, Response.Listener { response ->
                setLoading(false)

                val gson = Gson()
                var mahasiswa = gson.fromJson(response, TodoApi::class.java)
                if (mahasiswa != null)
                    FancyToast.makeText(requireContext(),"Data Berhasil Dihapus!",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show()
                allTodo()
                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(requireContext(),errors.getString("message"),FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show()
                } catch (e: java.lang.Exception) {
                    FancyToast.makeText(requireContext(),e.message,FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show()
                }
            }) {
            // Menambahkan header pada request
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers: HashMap<String, String> = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LAUNCH_ADD_ACTIVITY && resultCode == AppCompatActivity.RESULT_OK)
        {
            setLoading(true)
            allTodo()
        }
    }
}




////    val db by lazy { N}
//private lateinit var binding: FragmentBerandaBinding
//private lateinit var toDoViewModel: ToDoViewModel
//lateinit var adapter : TodoAdapter
//override fun onCreateView(
//    inflater: LayoutInflater, container: ViewGroup?,
//    savedInstanceState: Bundle?
//): View? {
//    // Inflate the layout for this fragment
//    return inflater.inflate(R.layout.fragment_beranda, container, false)
//
//
//    toDoViewModel = ViewModelProvider(this).get(ToDoViewModel::class.java)
//    toDoViewModel.readAllTodo.observe(viewLifecycleOwner, Observer { todo -> adapter.setData(todo)  })
//    //RecyclerView
//
//    binding.buttonCreate.setOnClickListener {
//        startActivity(Intent(requireContext(),EditActivity::class.java))
//    }
//}
//
//override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//    val layoutManager = LinearLayoutManager(context)
//    val adapter : TodoAdapter =
//
//    val rvBeranda : RecyclerView = view.findViewById(R.id.rv_beranda)
//
//    rvBeranda.layoutManager = layoutManager
//
//    rvBeranda.setHasFixedSize(true)
////        rvBeranda.adapter = adapter
////        rvBeranda.adapter = adapter
//}
//
//
//private fun setupRecyclerView() {
//    todoAdapter = TodoAdapter(arrayListOf(), object :
//        TodoAdapter.OnAdapterListener {
//        override fun onClick(toDoList: ToDoList) {
//            intentEdit(toDoList.id, Constant.TYPE_READ)
//        }
//        override fun onUpdate(toDoList: ToDoList) {
//            intentEdit(toDoList.id, Constant.TYPE_UPDATE)
//        }
//        override fun onDelete(toDoList: ToDoList) {
//            deleteDialog(toDoList)
//        }
//    })
//    rv_todo.apply {
//        layoutManager = LinearLayoutManager(requireContext().applicationContext)//application context in fragment?
//        adapter = todoAdapter
//    }
//}
//private fun deleteDialog(toDoList: ToDoList){
//    val alertDialog = AlertDialog.Builder(requireContext())
//    alertDialog.apply {
//        setTitle("Confirmation")
//        setMessage("Are You Sure to delete this data From ${toDoList.judul}?")
//        setNegativeButton("Cancel", DialogInterface.OnClickListener
//        { dialogInterface, i -> dialogInterface.dismiss()
//        })
//        setPositiveButton("Delete", DialogInterface.OnClickListener
//        { dialogInterface, i -> dialogInterface.dismiss()
//            CoroutineScope(Dispatchers.IO).launch {
//                db!!.ToDoListDAO().deleteToDo(toDoList)
//                loadData()
//            }
//        })
//    }
//    alertDialog.show()
//}
//
//override fun onStart() {
//    super.onStart()
//    loadData()
//}
////untuk load data yang tersimpan pada database yang sudah create
//
//fun loadData() {
//    CoroutineScope(Dispatchers.IO).launch {
//        val notes = db!!.ToDoListDAO().getNotes()
//        Log.d("MainActivity","dbResponse: $notes")
//        withContext(Dispatchers.Main){
//            todoAdapter.setData( notes )
//        }
//    }
//}
//fun setupListener() {
//    button_create.setOnClickListener{
//        intentEdit(0, Constant.TYPE_CREATE)
//    }
//}
////pick data dari Id yang sebagai primary key
//fun intentEdit(todoId : Int, intentType: Int){
//    startActivity(
//        Intent(requireActivity().applicationContext, EditActivity::class.java)
//            .putExtra("intent_id", todoId)
//            .putExtra("intent_type", intentType)
//    )
//}