package com.example.e_learning.Adapter



import android.content.Context
import android.content.Intent
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.e_learning.Activity.AddEditActivity
import com.example.e_learning.Activity.HomeActivity
import com.example.e_learning.Fragment.ToDoFragment
import com.example.e_learning.Fragment.ToDoFragment.Companion.LAUNCH_ADD_ACTIVITY
import com.example.e_learning.R
import com.example.e_learning.data.todoList.ToDoList
import java.util.*
import kotlin.collections.ArrayList
class TodoAdapter(private var todoList:  List<ToDoList>, context: Context) : RecyclerView.Adapter<TodoAdapter.ViewHolder>(), Filterable {
    private var filteredTodoList: MutableList<ToDoList>
    private val context: Context

    init {
        filteredTodoList = ArrayList(todoList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_todo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredTodoList.size
    }

    fun setTodoList(todoList: Array<ToDoList>) {
        this.todoList = todoList.toList()
        filteredTodoList = todoList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = filteredTodoList[position]
        holder.tvNama.text = todo.judul
        holder.tvNPM.text = todo.pesan
//        holder.tvFakultas.text = mahasiswa.fakultas
//        holder.tvProdi.text = mahasiswa.prodi

        holder.btnDelete.setOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("konfirmasi")
                .setMessage("Apakah anda yakin ingin menghapus data mahasiswa ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Hapus") { _, _ ->
                    val toDoFragment = ToDoFragment()
                     todo.id?.let { it1 ->
//                        toDoFragment.deleteMahasiswa(
//                            it1
//                        )
                    }
                }
                .show()
        }
        holder.cvTodo.setOnClickListener {
            val i = Intent(context, AddEditActivity::class.java)
//            val todoFragment = ToDoFragment()
            i.putExtra("id", todo.id)
            if (context is HomeActivity)
                context.startActivityForResult(i,ToDoFragment.LAUNCH_ADD_ACTIVITY)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<ToDoList> = java.util.ArrayList()
                if (charSequenceString.isEmpty()) {
                    filtered.addAll(todoList)
                } else {
                    for (todo in todoList) {
                        if (todo.judul.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add(todo)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredTodoList.clear()
                filteredTodoList.addAll(filterResults.values as List<ToDoList>)
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNama: TextView
        var tvNPM: TextView
        var cvTodo: CardView
        var btnDelete: ImageButton
//        var tvFakultas : TextView
//        var tvProdi : TextView

        init {
            tvNama = itemView.findViewById(R.id.tv_todo)
            tvNPM = itemView.findViewById(R.id.tv_pesan)
//            tvProdi = itemView.findViewById(R.id.tv_prodi)
//            tvFakultas = itemView.findViewById(R.id.tv_fakultas)
            btnDelete = itemView.findViewById(R.id.btn_delete)
            cvTodo = itemView.findViewById(R.id.cv_todo)
        }
    }
}

//
//import android.annotation.SuppressLint
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.e_learning.R
//import com.example.e_learning.data.todoList.ToDoList
//import kotlinx.android.synthetic.main.adapter_todo.view.*
//
//class TodoAdapter (private val todolist: ArrayList<ToDoList>, private val listener: OnAdapterListener) :
//    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
//        return TodoViewHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.adapter_todo,parent, false)
//        )
//
//
//    }
//    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
//        val todo = todolist[position]
//        holder.view.text_title.text = todo.judul
//        holder.view.text_title.setOnClickListener{
//            listener.onClick(todo)
//        }
//        holder.view.icon_edit.setOnClickListener {
//            listener.onUpdate(todo)
//        }
//        holder.view.icon_delete.setOnClickListener {
//            listener.onDelete(todo)
//        }
//    }
//    override fun getItemCount() = todolist.size
//    inner class TodoViewHolder( val view: View) : RecyclerView.ViewHolder(view)
//    {
//
//    }
//    @SuppressLint("NotifyDataSetChanged")
//    fun setData(list: List<ToDoList>){
//        todolist.clear()
//        todolist.addAll(list)
//        notifyDataSetChanged()
//    }
//    interface OnAdapterListener {
//        fun onClick(toDoList: ToDoList)
//        fun onUpdate(toDoList: ToDoList)
//        fun onDelete(toDoList: ToDoList)
//    }
//}
//
////class TodoAdapter() : RecyclerView.Adapter<ToDoViewHolder>()
////{
////
////
////}
////
////
////
////
////private var toDoList = emptyList<ToDoList>()
////
////inner class ToDoViewHolder(val itemBinding: AdapterTodoBinding) : RecyclerView.ViewHolder(itemBinding.root)
////{
////    fun bindItem(toDoList: ToDoList){
////        itemBinding.textTitle.text = toDoList.judul
////        itemBinding.textTitle.setOnClickListener {}
////        itemBinding.iconEdit.setOnClickListener{}
////        itemBinding.iconDelete.setOnClickListener{}
////    }
////}
////
////override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
////    return ToDoViewHolder(AdapterTodoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
////}
////
////override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
////    val currentItem = toDoList[position]
////    holder.bindItem(currentItem)
////}
////
////override fun getItemCount(): Int {
////    return toDoList.size
////}
////
////interface OnAdapterListener {
////    fun onClick(toDoList: ToDoList)
////    fun onUpdate(toDoList: ToDoList)
////    fun onDelete(toDoList: ToDoList)
////}
////
////fun setData(toDoList: List<ToDoList>)
////{
////    this.toDoList = toDoList
////    notifyDataSetChanged()
////}
//
////        private var edit: ImageView = itemView.findViewById(R.id.icon_edit)
////        private var delete: ImageView = itemView.findViewById(R.id.icon_delete)
////        private var judul:  TextView = itemView.findViewById(R.id.text_title)