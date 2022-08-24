package com.dimasbintang.codingtest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dimasbintang.codingtest.data.Lokasi

class LokasiRecyclerViewAdapter(val context: Context, val lokasiClickInterface: LokasiClickInterface): RecyclerView.Adapter<LokasiRecyclerViewAdapter.ViewHolder>() {

    private val allLokasi = ArrayList<Lokasi>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val lokasiName = itemView.findViewById<TextView>(R.id.txt_name)
        val lokasiStatus = itemView.findViewById<TextView>(R.id.txt_status)
        val textInactive = itemView.findViewById<TextView>(R.id.txt_inactive)
        val bgLayout = itemView.findViewById<RelativeLayout>(R.id.bg_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_lokasi, parent, false);
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.lokasiName.setText(allLokasi.get(position).name)

        if (allLokasi.get(position).status == true){
            holder.lokasiStatus.visibility =View.VISIBLE
            holder.textInactive.visibility =View.GONE
        } else {
            holder.lokasiStatus.visibility =View.GONE
            holder.textInactive.visibility =View.VISIBLE
        }

        holder.itemView.setOnClickListener {
            lokasiClickInterface.onLokasiClick(allLokasi.get(position))
        }

        if(position % 2 == 0) {
            holder.bgLayout.setBackgroundResource(R.color.white);
        } else {
            holder.bgLayout.setBackgroundResource(R.color.light_gray);
        }

    }

    override fun getItemCount(): Int {
        return allLokasi.size
    }

    fun updateList(newList: List<Lokasi>){
        allLokasi.clear()
        allLokasi.addAll(newList)
        notifyDataSetChanged()
    }


}

interface LokasiClickInterface{
    fun onLokasiClick(lokasi: Lokasi)
}