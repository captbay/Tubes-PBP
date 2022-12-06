package com.example.e_learning.home.tugas.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_learning.R

class TugasAdapter(private val data: Array<Tugas>) : RecyclerView.Adapter<TugasAdapter.viewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_tugas, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvNama.text = currentItem.namaTugas
        holder.tvDeskripsi.text = currentItem.deskripsiTugas
        holder.tvDurasi.text = currentItem.durasiTugas
        holder.tvHadiah.text = currentItem.hadiahTugas
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tv_namaTugas)
        val tvDeskripsi : TextView = itemView.findViewById(R.id.tv_deskripsiTugas)
        val tvDurasi : TextView = itemView.findViewById(R.id.tv_durasiTugas)
        val tvHadiah : TextView = itemView.findViewById(R.id.tv_hadiahTugas)
    }
}