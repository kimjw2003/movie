package com.example.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.movie.data.Base
import com.example.movie.retrofit.MovieClient
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private var title :String? = null
    private var actor : String? = null
    private var poster :String? = null
    private var country :String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        title = intent.getStringExtra("title")
        actor = intent.getStringExtra("actor")
        poster = intent.getStringExtra("poster")
        country = intent.getStringExtra("countryInfo")

//        Glide.with(applicationContext)
//            .load(poster)
//            .into(detail_poster)

        detail_title.text = title

        getDetailMovie()
    }

    private fun getDetailMovie(){
        MovieClient.retrofitService.getMovie("kmdb_new2", "Y", "1QS3HYA074P8X6W4TEF3", "",
            ""+detail_title.text.toString(), "", "극영화", "1")
            .enqueue(object : retrofit2.Callback<Base>{
                override fun onResponse(call: Call<Base>, response: Response<Base>) {
                    Log.d("Logd", country)                                                  // 로그에 어느 나라가 찍혔는지 확인

                    val res = response.body()?.Data?.get(0)?.Result?.get(0)              // res에 저만큼 넣어서 코드간결화 가능

                    val titleEng_idx: Int = res?.titleEng!!.indexOf("(")                   // "("전까지만 출력하도록 설정

                    if(country == "미국"){                                                        // 나라를 미국으로 설정하면
                        detail_titleEng.text = res.titleEng
                    }else {
                        Log.d("Logd", res.titleEng)
                        detail_titleEng.text = res.titleEng.substring(0, titleEng_idx)
                    }

                    Log.d("Logd", "out")

                    if(res.repRlsDate == ""){               //만약에 개봉년도가 비어있다면
                        Log.d("Logd", "empty")
                        detail_openDate.text = res.repRatDate //심의년도로 대체하라
                    }else{
                        detail_openDate.text = res.repRlsDate //아니면 그대로 개봉년도 넣고
                    }
                }

                override fun onFailure(call: Call<Base>, t: Throwable) {
                }
            })
    }
}