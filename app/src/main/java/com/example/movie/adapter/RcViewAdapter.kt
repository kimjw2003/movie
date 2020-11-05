package com.example.movie.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.DetailActivity
import com.example.movie.R
import com.example.movie.data.MovieData
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.ArrayList

class RcViewAdapter (val movieData: ArrayList<MovieData>) : RecyclerView.Adapter<RcViewAdapter.Holder>(){

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var title = itemView.findViewById<TextView>(R.id.movie_title_Tv)
        var actor = itemView.findViewById<TextView>(R.id.movie_actor_Tv)
        var poster = itemView.findViewById<ImageView>(R.id.movie_poster_Iv)

        fun bind(data : MovieData){
            title.text = data.title
            actor.text = data.actor

            Glide.with(itemView.context)
                .load(data.poster)
                .into(poster)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("title", data.title)
                intent.putExtra("actor", data.actor)
                intent.putExtra("poster", data.poster)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(movieData[position])
    }

    override fun getItemCount(): Int {
        return movieData.size
    }
}