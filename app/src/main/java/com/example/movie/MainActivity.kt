package com.example.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import com.example.movie.data.Base
import com.example.movie.retrofit.MovieClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search_Btn.setOnClickListener {
            getMovie()
        }
    }

    private fun getMovie(){
        MovieClient.retrofitService.getMovie("kmdb_new2", "Y", "1QS3HYA074P8X6W4TEF3", "대한민국",
            "20201028", ""+search_Et.text.toString(), ""
        ).enqueue(object : retrofit2.Callback<Base> {
            override fun onResponse(call: Call<Base>, response: Response<Base>) {
                Log.d("Logd", "good")



                title_Tv.text = response.body()?.KMAQuery
                actor_Tv.text = response.body()?.Data?.get(0)?.Result?.get(0)?.actors?.actor?.get(0)?.actorNm
                actor2_Tv.text = response.body()?.Data?.get(0)?.Result?.get(0)?.actors?.actor?.get(1)?.actorNm

            }

            override fun onFailure(call: Call<Base>, t: Throwable) {
                Log.d("Logd", t.message.toString())
            }

        })
    }
}