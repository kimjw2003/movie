package com.example.movie

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movie.adapter.RcViewAdapter
import com.example.movie.data.Base
import com.example.movie.data.MovieData
import com.example.movie.retrofit.MovieClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.rcView
import retrofit2.Call
import retrofit2.Response

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
             ""+search_Et.text.toString(), "", "극영화", "20"
        ).enqueue(object : retrofit2.Callback<Base> {
            @SuppressLint("CheckResult")
            override fun onResponse(call: Call<Base>, response: Response<Base>) {
                Log.d("Logd", "onResponse")

                    Log.d("Logd","검색된 영화 수 : "+ response.body()?.TotalCount.toString())

                    var movieList = ArrayList<MovieData>()
                        response.body()?.Data?.get(0)?.Result?.forEach {it ->       //response.body()?.Data?.get(0)?.Result -> it 으로 정의
                            var postIdx = it.posters.indexOf("|")
                            if(postIdx == -1){
                                postIdx = it.posters.length
                            }
                            movieList.add(MovieData(it.titleEtc!!.substring(0, it.titleEtc!!.indexOf("^")),
                                it.actors?.actor?.get(0)?.actorNm!!,
                                if(it.posters.isNotEmpty()) it.posters.substring(0, postIdx) else ""))
                    }

                    val adapter = RcViewAdapter(movieList)
                    rcView.adapter = adapter
                    rcView.layoutManager = LinearLayoutManager(this@MainActivity)
            }

            override fun onFailure(call: Call<Base>, t: Throwable) {
                Log.d("Logd", t.message.toString())
            }
        })
    }
}