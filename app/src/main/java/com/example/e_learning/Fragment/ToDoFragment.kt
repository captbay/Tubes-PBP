package com.example.e_learning.Fragment
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.e_learning.Activity.AddEditActivity
import com.example.e_learning.Activity.HomeActivity
//import com.example.e_learning.Activity.
import com.example.e_learning.Adapter.TodoAdapter
import com.example.e_learning.Api.TodoApi
import com.example.e_learning.data.todoList.Constant
import com.example.e_learning.data.todoList.ToDoList
import com.example.e_learning.databinding.FragmentTodoBinding
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import com.example.e_learning.data.todoList.ResponseData


//import kotlinx.android.synthetic.main.fragment_beranda.*


class ToDoFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private var todoAdapter: TodoAdapter?= null
    private var queue:RequestQueue? = null

    companion object {
        const val LAUNCH_ADD_ACTIVITY = 123
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    //Test
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        queue = Volley.newRequestQueue(requireContext())

        binding.srTodo.setOnRefreshListener({ allTodo() })
        binding.svTodo.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(s: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(s: String?): Boolean {
                todoAdapter!!.filter.filter(s)
                return false
            }
        })

        binding.fabAdd.setOnClickListener{
            val i = Intent(requireActivity(), AddEditActivity::class.java)
            startActivityForResult(i, LAUNCH_ADD_ACTIVITY)
        }
        todoAdapter = TodoAdapter(ArrayList(),requireContext() )
        binding.rvTodo.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTodo.adapter = todoAdapter
        allTodo()
    }

    private fun allTodo() {
        binding.srTodo!!.isRefreshing = true
        val stringRequest: StringRequest = object : StringRequest(Method.GET, TodoApi.GET_ALL_URL, Response.Listener { response ->
                val gson = Gson()
                var  todolist: Array<ToDoList> = gson.fromJson(response, ResponseData::class.java).data.toTypedArray()
                todoAdapter!!.setTodoList(todolist)
                todoAdapter!!.filter.filter(binding.svTodo!!.query)
                binding.srTodo!!.isRefreshing = false

                if(!todolist.isEmpty())
                    Toast.makeText(context, "Data Berhasil Diambil!", Toast.LENGTH_SHORT)
                        .show()
                else
                    Toast.makeText(context, "Data Kosong!", Toast.LENGTH_SHORT)
                        .show()
            }, Response.ErrorListener { error ->
                binding.srTodo!!.isRefreshing = false
                try{
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        requireActivity(),
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
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
        queue!!.add(stringRequest)
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