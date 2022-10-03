package com.example.e_learning

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_learning.Data.ToDoList.Constant
import com.example.e_learning.Data.ToDoList.ToDoList
//import com.example.e_learning.Data.ToDoList.ToDoViewModel
import com.example.e_learning.Data.ToDoList.TodoDB
import com.example.e_learning.databinding.FragmentBerandaBinding
import kotlinx.android.synthetic.main.fragment_beranda.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BerandaFragment : Fragment() {
    val db by lazy { activity?.let{TodoDB(it)} }
    private var _binding:FragmentBerandaBinding? = null
    private val binding get() = _binding!!
    lateinit var todoAdapter: TodoAdapter
    override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBerandaBinding.inflate(inflater, container ,false)
        val view = binding.root
        return view
    }
    //Test
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setupListener()
//        setupRecyclerView()
    }

    private fun setupRecyclerView() {
//        todoAdapter = TodoAdapter(arrayListOf(), object :
//            TodoAdapter.OnAdapterListener {
//            override fun onClick(toDoList: ToDoList) {
//                intentEdit(toDoList.id, Constant.TYPE_READ)
//            }
//            override fun onUpdate(toDoList: ToDoList) {
//                intentEdit(toDoList.id, Constant.TYPE_UPDATE)
//            }
//            override fun onDelete(toDoList: ToDoList) {
//                deleteDialog(toDoList)
//            }
//        })
//        rv_beranda.apply {
//            layoutManager = LinearLayoutManager(requireContext().applicationContext)//application context in fragment?
//            adapter = todoAdapter
//        }
    }
    private fun deleteDialog(toDoList: ToDoList){
//        val alertDialog = AlertDialog.Builder(requireContext())
//        alertDialog.apply {
//            setTitle("Confirmation")
//            setMessage("Are You Sure to delete this data From ${toDoList.judul}?")
//            setNegativeButton("Cancel", DialogInterface.OnClickListener
//            { dialogInterface, i -> dialogInterface.dismiss()
//            })
//            setPositiveButton("Delete", DialogInterface.OnClickListener
//            { dialogInterface, i -> dialogInterface.dismiss()
//                CoroutineScope(Dispatchers.IO).launch {
//                    db!!.ToDoListDAO().deleteNote(toDoList)
//                    loadData()
//                }
//            })
//        }
//        alertDialog.show()
    }
    override fun onStart() {
        super.onStart()
        loadData()
    }
    //untuk load data yang tersimpan pada database yang sudah create

    fun loadData() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val notes = db!!.ToDoListDAO().getNotes()
//            Log.d("MainActivity","dbResponse: $notes")
//            withContext(Dispatchers.Main){
//                todoAdapter.setData( notes )
//            }
//        }
    }
    fun setupListener() {
//        button_create.setOnClickListener{
//            intentEdit(0,Constant.TYPE_CREATE)
//        }
    }
    //pick data dari Id yang sebagai primary key
    fun intentEdit(noteId : Int, intentType: Int){
//        startActivity(
//            Intent(requireActivity().applicationContext, EditActivity::class.java)
//                .putExtra("intent_id", noteId)
//                .putExtra("intent_type", intentType)
//        )
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
