package com.example.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.movie.data.Base
import com.example.movie.retrofit.MovieClient
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

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
       // detail_actor.text = actor.toString()

        getDetailMovie()
    }

    fun getDetailMovie(){
        MovieClient.retrofitService.getMovie("kmdb_new2", "Y", "1QS3HYA074P8X6W4TEF3", "",
            ""+detail_title.text.toString(), "", "극영화", "1")
            .enqueue(object : retrofit2.Callback<Base>{
                override fun onResponse(call: Call<Base>, response: Response<Base>) {

                    var titleEng_idx: Int = response.body()?.Data?.get(0)?.Result?.get(0)?.titleEng!!.indexOf("(")

                    //detail_director.text = response.body()?.Data?.get(0)?.Result?.get(0)?.directors?.director?.get(0)?.directorNm
                    detail_titleEng.text = response.body()?.Data?.get(0)?.Result?.get(0)?.titleEng!!.substring(0, titleEng_idx)
                    detail_openDate.text = response.body()?.Data?.get(0)?.Result?.get(0)?.repRlsDate
                }

                override fun onFailure(call: Call<Base>, t: Throwable) {

                }

            })
    }
}