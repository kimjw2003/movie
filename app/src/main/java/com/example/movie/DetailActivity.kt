package com.example.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.movie.adapter.MyPagerAdapter
import com.example.movie.data.Base
import com.example.movie.retrofit.MovieClient
import com.example.movie.tabFragment.FirstFragment
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

        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        viewpager_main.adapter = fragmentAdapter

        tabs_main.setupWithViewPager(viewpager_main)


        title = intent.getStringExtra("title")
        actor = intent.getStringExtra("actor")
        poster = intent.getStringExtra("poster")
        country = intent.getStringExtra("countryInfo")

        detail_title.text = title

//        Glide.with(applicationContext)
//            .load(poster)
//            .into(detail_poster)

        getDetailMovie()
    }

    private fun getDetailMovie(){
        MovieClient.retrofitService.getMovie("kmdb_new2", "Y", "1QS3HYA074P8X6W4TEF3", "",
            ""+title, "", "극영화", "1")
            .enqueue(object : retrofit2.Callback<Base>{
                override fun onResponse(call: Call<Base>, response: Response<Base>) {
                    Log.d("Logd", country)                                                  // 로그에 어느 나라가 찍혔는지 확인
                    val res = response.body()?.Data?.get(0)?.Result?.get(0)              // res에 저만큼 넣어서 코드간결화 가능

                    val titleEng_idx: Int = res?.titleEng!!.indexOf("(")                   // "("전까지만 출력하도록 설정


                    if(res.titleEng.contains("(")){
                        Log.d("Logd", "(포함")
                        detail_titleEng.text = res.titleEng.substring(0, titleEng_idx)

                    }else{
                        Log.d("Logd", res.titleEng)
                        detail_titleEng.text = res.titleEng
                    }

                    if(res.repRlsDate == ""){               //만약에 개봉년도가 비어있다면
                        Log.d("Logd", "개봉년도 is empty")
                        detail_openDate.text = res.repRatDate //심의년도로 대체하라
                            if(res.repRatDate == "") {          //심의년도까지 비어있으면
                                detail_openDate.text = "알수없음"
                            }
                    }else{
                        detail_openDate.text = res.repRlsDate //아니면 그대로 개봉년도 넣고
                    }

                }

                override fun onFailure(call: Call<Base>, t: Throwable) {
                }
            })
    }
}