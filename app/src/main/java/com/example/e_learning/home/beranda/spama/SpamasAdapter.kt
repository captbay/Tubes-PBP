package com.example.e_learning.home.beranda.spama



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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class SpamasAdapter(private var spamas : List<Spamas>, context: Context, fragment: Fragment) :
    RecyclerView.Adapter<SpamasAdapter.ViewHolder>(),Filterable{
    private var filteredSpamas : MutableList<Spamas>
    private val context : Context
    private val fragment : Fragment

    init {
        filteredSpamas = ArrayList(spamas)
        this.context = context
        this.fragment = fragment
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var tvNamaSpama : TextView
        var tvPoinSpama : TextView
        var tvKetSpama : TextView
        var tvTglDibuat: TextView
//        var status : TextView
        var cvTodo : CardView
        var btnDelete : ImageButton

        init{
            tvNamaSpama = itemView.findViewById(R.id.tv_namaSpama)
            tvPoinSpama = itemView.findViewById(R.id.tv_poinSpama)
            tvKetSpama = itemView.findViewById(R.id.tv_ketSpama)
            tvTglDibuat= itemView.findViewById(R.id.tv_tglDibuatSpama)
            cvTodo = itemView.findViewById(R.id.cv_spama)
            btnDelete = itemView.findViewById(R.id.btn_delete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_spamas,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spamalist = filteredSpamas[position]
        holder.tvNamaSpama.text = spamalist.namaSpama
        holder.tvPoinSpama.text = spamalist.poinSpama
        holder.tvKetSpama.text = spamalist.ketSpama
        holder.tvTglDibuat.text = spamalist.tglDibuat

        holder.btnDelete.setOnClickListener{
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin menghapus data spama ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Yakin"){_, _ ->
                    if(fragment is SpamasFragment) spamalist.id?.let {
                            it1->fragment.deleteSpamas(
                        it1
                    )
                    }
                }.show()
        }

        holder.cvTodo.setOnClickListener{
            val i = Intent(context, AddEditActivity::class.java)
            i.putExtra("id", spamalist.id)
            if(context is HomeActivity)
                context.startActivityForResult(i, SpamasFragment.LAUNCH_ADD_ACTIVITY)
        }
    }

    override fun getItemCount(): Int {
        return filteredSpamas.size
    }

    fun setSpamas(spamas: Array<Spamas>)
    {
        this.spamas = spamas.toList()
        filteredSpamas = spamas.toMutableList()
    }

    override fun getFilter(): Filter {
        return object : Filter()
        {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<Spamas> = java.util.ArrayList()
                if(charSequenceString.isEmpty())
                {
                    filtered.addAll(spamas)
                }else
                {
                    for(spama in spamas)
                    {
                        if(spama.namaSpama.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        )filtered.add(spama)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults(p0: CharSequence, p1: FilterResults) {
                filteredSpamas.clear()
                filteredSpamas.addAll(p1.values as List<Spamas>)
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
//import com.example.e_learning.data.spamas.ToDoList
//import kotlinx.android.synthetic.main.adapter_spama.view.*
//
//class TodoAdapter (private val spamalist: ArrayList<ToDoList>, private val listener: OnAdapterListener) :
//    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
//        return TodoViewHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.adapter_spama,parent, false)
//        )
//
//
//    }
//    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
//        val spama = spamalist[position]
//        holder.view.text_title.text = spama.judul
//        holder.view.text_title.setOnClickListener{
//            listener.onClick(spama)
//        }
//        holder.view.icon_edit.setOnClickListener {
//            listener.onUpdate(spama)
//        }
//        holder.view.icon_delete.setOnClickListener {
//            listener.onDelete(spama)
//        }
//    }
//    override fun getItemCount() = spamalist.size
//    inner class TodoViewHolder( val view: View) : RecyclerView.ViewHolder(view)
//    {
//
//    }
//    @SuppressLint("NotifyDataSetChanged")
//    fun setData(list: List<ToDoList>){
//        spamalist.clear()
//        spamalist.addAll(list)
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