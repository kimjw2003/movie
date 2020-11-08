package com.example.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.movie.data.Base
import com.example.movie.retrofit.MovieClient
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import java.security.cert.CRLSelector
import javax.security.auth.callback.Callback

class DetailActivity : AppCompatActivity() {

    var title :String? = null
    var actor : String? = null
    var poster :String? = null
    var country :String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        title = intent.getStringExtra("title")
        actor = intent.getStringExtra("actor")
        poster = intent.getStringExtra("poster")
        country = intent.getStringExtra("countryInfo")

        Glide.with(applicationContext)
            .load(poster)
            .into(detail_poster)

        detail_title.text = title
       // detail_actor.text = actor.toString()

        getDetailMovie()
    }

    private fun getDetailMovie(){
        MovieClient.retrofitService.getMovie("kmdb_new2", "Y", "1QS3HYA074P8X6W4TEF3", "",
            ""+detail_title.text.toString(), "", "극영화", "1")
            .enqueue(object : retrofit2.Callback<Base>{
                override fun onResponse(call: Call<Base>, response: Response<Base>) {
                    Log.d("Logd", country)

                    var res = response.body()?.Data?.get(0)?.Result?.get(0)

                    var titleEng_idx: Int = res?.titleEng!!.indexOf("(")

                    //detail_director.text = response.body()?.Data?.get(0)?.Result?.get(0)?.directors?.director?.get(0)?.directorNm

                    if(country == "미국"){                                                        //나라를 미국으로 설정하면
                        detail_titleEng.text = res.titleEng
                    }else {
                        Log.d("Logd", res.titleEng!!)
                        detail_titleEng.text = res.titleEng!!.substring(0, titleEng_idx)
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