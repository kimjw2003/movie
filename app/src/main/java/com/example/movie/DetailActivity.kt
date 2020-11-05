package com.example.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    var title :String? = null
    var actor : String? = null
    var poster :String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        title = intent.getStringExtra("title")
        actor = intent.getStringExtra("actor")
        poster = intent.getStringExtra("poster")

        Glide.with(applicationContext)
            .load(poster)
            .into(detail_poster)

        detail_title.text = title.toString()
        detail_actor.text = actor.toString()
    }
}