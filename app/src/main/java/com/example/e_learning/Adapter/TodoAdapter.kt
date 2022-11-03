package com.example.e_learning.Adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_learning.R
import com.example.e_learning.data.todoList.ToDoList
import kotlinx.android.synthetic.main.adapter_todo.view.*

class TodoAdapter (private val todolist: ArrayList<ToDoList>, private val listener: OnAdapterListener) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_todo,parent, false)
        )


    }
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todolist[position]
        holder.view.text_title.text = todo.judul
        holder.view.text_title.setOnClickListener{
            listener.onClick(todo)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(todo)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(todo)
        }
    }
    override fun getItemCount() = todolist.size
    inner class TodoViewHolder( val view: View) : RecyclerView.ViewHolder(view)
    {

    }
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<ToDoList>){
        todolist.clear()
        todolist.addAll(list)
        notifyDataSetChanged()
    }
    interface OnAdapterListener {
        fun onClick(toDoList: ToDoList)
        fun onUpdate(toDoList: ToDoList)
        fun onDelete(toDoList: ToDoList)
    }
}

//class TodoAdapter() : RecyclerView.Adapter<ToDoViewHolder>()
//{
//
//
//}
//
//
//
//
//private var toDoList = emptyList<ToDoList>()
//
//inner class ToDoViewHolder(val itemBinding: AdapterTodoBinding) : RecyclerView.ViewHolder(itemBinding.root)
//{
//    fun bindItem(toDoList: ToDoList){
//        itemBinding.textTitle.text = toDoList.judul
//        itemBinding.textTitle.setOnClickListener {}
//        itemBinding.iconEdit.setOnClickListener{}
//        itemBinding.iconDelete.setOnClickListener{}
//    }
//}
//
//override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
//    return ToDoViewHolder(AdapterTodoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
//}
//
//override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
//    val currentItem = toDoList[position]
//    holder.bindItem(currentItem)
//}
//
//override fun getItemCount(): Int {
//    return toDoList.size
//}
//
//interface OnAdapterListener {
//    fun onClick(toDoList: ToDoList)
//    fun onUpdate(toDoList: ToDoList)
//    fun onDelete(toDoList: ToDoList)
//}
//
//fun setData(toDoList: List<ToDoList>)
//{
//    this.toDoList = toDoList
//    notifyDataSetChanged()
//}

//        private var edit: ImageView = itemView.findViewById(R.id.icon_edit)
//        private var delete: ImageView = itemView.findViewById(R.id.icon_delete)
//        private var judul:  TextView = itemView.findViewById(R.id.text_title)