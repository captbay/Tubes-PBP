//package com.example.e_learning
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.e_learning.entity.Kelas
//
//class RVKelasAdapter(private val data: Array<Kelas>) : RecyclerView.Adapter<RVKelasAdapter.viewHolder>()  {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
//
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_kelas, parent, false)
//        return viewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: viewHolder, position: Int) {
//        val currentItem = data[position]
//        holder.tvMatpel.text = currentItem.mataPelajaran
//        holder.tvTahunAjaran.text = currentItem.tahunAjaran
//        holder.tvSesi.text = currentItem.sesiKelas
//        holder.tvGuru.text = currentItem.guruPengajar
//        holder.tvGambar.setImageResource(currentItem.photo)
//    }
//
//    override fun getItemCount(): Int {
//        return data.size
//    }
//
//    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val tvMatpel: TextView = itemView.findViewById(R.id.tv_namaMatPel)
//        val tvTahunAjaran : TextView = itemView.findViewById(R.id.tv_tahunAjaran)
//        val tvSesi : TextView = itemView.findViewById(R.id.tv_sesiKelas)
//        val tvGuru : TextView = itemView.findViewById(R.id.tv_guru)
//        val tvGambar : ImageView = itemView.findViewById(R.id.iv_image)
//    }
//}