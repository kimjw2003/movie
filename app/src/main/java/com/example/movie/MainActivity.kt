package com.example.movie

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
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
             ""+search_Et.text.toString(), "", "극영화"
        ).enqueue(object : retrofit2.Callback<Base> {
            @SuppressLint("CheckResult")
            override fun onResponse(call: Call<Base>, response: Response<Base>) {
                Log.d("Logd", "good")

                // 포스터 선언하기
                var poster:String = response.body()?.Data?.get(0)?.Result?.get(0)?.posters!!
                // 포스터 1개만 보여주기
                var idx:Int = poster.indexOf("|")

                // 만약에 검색결과 영화가 1개면
                if(response.body()?.TotalCount.toString() == "1") {
                Log.d("Logd", "1")
                // 포스터 적용
                Glide.with(poster_Iv)
                    .load(poster.substring(0, idx))
                    .into(poster_Iv)

                //제목 ^앞까지만 보여주라고 설정하기
                var idx2:Int = response.body()?.Data?.get(0)?.Result?.get(0)?.titleEtc!!.indexOf("^")
                // 제목 보여주기
                title_Tv.text = response.body()?.Data?.get(0)?.Result?.get(0)?.titleEtc!!.substring(0, idx2)
                // 주연배우
                actor_Tv.text = response.body()?.Data?.get(0)?.Result?.get(0)?.actors?.actor?.get(0)?.actorNm
                //주연배우가 2명이상일 경우 if문 실행
                if(response.body()?.Data?.get(0)?.Result?.get(0)?.actors?.actor?.size!! > 1) {
                    actor2_Tv.text =
                        response.body()?.Data?.get(0)?.Result?.get(0)?.actors?.actor?.get(1)?.actorNm
                    }
                }else {
                    Log.d("Logd","검색된 영화 수 : "+ response.body()?.TotalCount.toString())
                }

            }

            override fun onFailure(call: Call<Base>, t: Throwable) {
                Log.d("Logd", t.message.toString())
            }

        })
    }
}