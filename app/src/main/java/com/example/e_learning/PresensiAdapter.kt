//package com.example.e_learning
//
//import android.annotation.SuppressLint
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.e_learning.entity.Presensi
//
//class PresensiAdapter (private val listpresensi: ArrayList<Presensi>, private val listener: OnAdapterListener) :
//    RecyclerView.Adapter<PresensiAdapter.PresensiViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresensiViewHolder {
//        return PresensiViewHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.adapter_presensi,parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: PresensiViewHolder, position: Int) {
//        val note = listpresensi[position]
//        holder.view.text_tanggal.text = note.tanggal
//        holder.view.setOnClickListener{
//            listener.onClick(note)
//        }
//        holder.view.icon_edit.setOnClickListener {
//            listener.onUpdate(note)
//        }
//        holder.view.icon_delete.setOnClickListener {
//            listener.onDelete(note)
//        }
//    }
//    override fun getItemCount() = listpresensi.size
//    inner class PresensiViewHolder( val view: View) : RecyclerView.ViewHolder(view)
//    @SuppressLint("NotifyDataSetChanged")
//    fun setData(list: List<Presensi>){
//        listpresensi.clear()
//        listpresensi.addAll(list)
//        notifyDataSetChanged()
//    }
//    interface OnAdapterListener {
//        fun onClick(presensi: Presensi)
//        fun onUpdate(presensi: Presensi)
//        fun onDelete(presensi: Presensi)
//    }
//}