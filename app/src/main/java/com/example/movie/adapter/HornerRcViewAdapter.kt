package com.example.movie.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.data.HornerData

class HornerRcViewAdapter(val hornerData: ArrayList<HornerData>):RecyclerView.Adapter<HornerRcViewAdapter.Holder>() {

    inner class Holder(itemView : View): RecyclerView.ViewHolder(itemView) {
        var horner = itemView.findViewById<TextView>(R.id.horner)

        fun bind(data: HornerData) {
            horner.text = data.hornerData
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HornerRcViewAdapter.Holder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horner, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: HornerRcViewAdapter.Holder, position: Int) {
        holder.bind(hornerData[position])
    }

    override fun getItemCount(): Int {
        return hornerData.size
    }
}