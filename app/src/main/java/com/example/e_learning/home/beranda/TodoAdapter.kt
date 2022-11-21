package com.example.e_learning.home.beranda



import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.e_learning.R
import com.example.e_learning.home.HomeActivity
import com.example.e_learning.home.beranda.todoList.ToDoList
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.collections.ArrayList

class TodoAdapter(private var todoList : List<ToDoList>, context: Context, fragment: Fragment) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>(),Filterable{
    private var filteredTodoList : MutableList<ToDoList>
    private val context : Context
    private val fragment : Fragment

    init {
        filteredTodoList = ArrayList(todoList)
        this.context = context
        this.fragment = fragment
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var tvJudul : TextView
        var tvPesan : TextView
        var tglDibuat : TextView
        var tglDeadline : TextView
        var status : TextView
        var cvTodo : CardView
        var btnDelete : ImageButton

        init{
            tvJudul = itemView.findViewById(R.id.tv_todo)
            tvPesan = itemView.findViewById(R.id.tv_pesan)
            tglDibuat = itemView.findViewById(R.id.tv_tgldibuat)
            tglDeadline = itemView.findViewById(R.id.tv_tgldeadline)
            status = itemView.findViewById(R.id.tv_status)
            cvTodo = itemView.findViewById(R.id.cv_todo)
            btnDelete = itemView.findViewById(R.id.btn_delete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_todo,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todolist = filteredTodoList[position]
        holder.tvJudul.text = todolist.judul
        holder.tvPesan.text = todolist.pesan
        holder.tglDibuat.text = todolist.tglDibuat.toString()
        holder.tglDeadline.text = todolist.tglDeadline.toString()
        holder.status.text = todolist.status.toString()

        holder.btnDelete.setOnClickListener{
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin menghapus data todo ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Yakin"){_, _ ->
                    if(fragment is ToDoFragment) todolist.id?.let {
                            it1->fragment.deleteTodo(
                        it1
                    )
                    }
                }.show()
        }

        holder.cvTodo.setOnClickListener{
            val i = Intent(context, AddEditActivity::class.java)
            i.putExtra("id", todolist.id)
            if(context is HomeActivity)
                context.startActivityForResult(i, ToDoFragment.LAUNCH_ADD_ACTIVITY)
        }
    }

    override fun getItemCount(): Int {
        return filteredTodoList.size
    }

    fun setTodoList(todoList: Array<ToDoList>)
    {
        this.todoList = todoList.toList()
        filteredTodoList = todoList.toMutableList()
    }

    override fun getFilter(): Filter {
        return object : Filter()
        {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<ToDoList> = java.util.ArrayList()
                if(charSequenceString.isEmpty())
                {
                    filtered.addAll(todoList)
                }else
                {
                    for(todo in todoList)
                    {
                        if(todo.judul.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        )filtered.add(todo)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults(p0: CharSequence, p1: FilterResults) {
                filteredTodoList.clear()
                filteredTodoList.addAll(p1.values as List<ToDoList>)
                notifyDataSetChanged()
            }
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