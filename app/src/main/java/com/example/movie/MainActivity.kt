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
             ""+search_Et.text.toString(), "", "극영화"
        ).enqueue(object : retrofit2.Callback<Base> {
            @SuppressLint("CheckResult")
            override fun onResponse(call: Call<Base>, response: Response<Base>) {
                Log.d("Logd", "good")

                //제목 ^앞까지만 보여주라고 설정하기
                var idx2:Int = response.body()?.Data?.get(0)?.Result?.get(0)?.titleEtc!!.indexOf("^")
                // 포스터 선언하기
                var poster:String = response.body()?.Data?.get(0)?.Result?.get(0)?.posters!!
                // 포스터 1개만 보여주기
                var idx:Int = poster.indexOf("|")

                // 만약에 검색결과 영화가 1개면
                if(response.body()?.TotalCount.toString() == "1") {
                Log.d("Logd", "movie 개수 = 1")

                    //리사이클러뷰 리스트 시1발
                    var list = ArrayList<MovieData>()
                    list.add(MovieData(response.body()?.Data?.get(0)?.Result?.get(0)?.titleEtc!!.substring(0, idx2),
                        response.body()?.Data?.get(0)?.Result?.get(0)?.actors?.actor?.get(0)?.actorNm!!, poster.substring(0, idx)))
                    val adapter = RcViewAdapter(list)
                    rcView.adapter = adapter
                    rcView.layoutManager = LinearLayoutManager(this@MainActivity)

                if(response.body()?.Data?.get(0)?.Result?.get(0)?.actors?.actor?.size!! > 1) {

                    }
                }else {
                    Log.d("Logd","검색된 영화 수 : "+ response.body()?.TotalCount.toString())

                    var list = ArrayList<MovieData>()
                    response.body()!!.Data!!.forEach {data ->
                        data.Result?.forEach {
                            var postIdx = it.posters.indexOf("|")
                            if(postIdx == -1){
                                postIdx = it.posters.length
                            }

                            list.add(MovieData(it.titleEtc!!.substring(0, it.titleEtc!!.indexOf("^")),
                                it.actors?.actor?.get(0)?.actorNm!!,
                                if(it.posters.isNotEmpty()) it.posters.substring(0, postIdx) else ""))
                        }
                    }


                    val adapter = RcViewAdapter(list)
                    rcView.adapter = adapter
                    rcView.layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }

            override fun onFailure(call: Call<Base>, t: Throwable) {
                Log.d("Logd", t.message.toString())
            }
        })
    }
}