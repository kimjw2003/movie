package com.example.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.adapter.RcViewAdapter
import com.example.movie.data.MovieData
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.item_movie.*


class Main2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


    }
}