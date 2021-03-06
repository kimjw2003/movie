package com.example.movie.tabFragment

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.data.MovieBase
import com.example.movie.retrofit.MovieClient
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*
import okhttp3.internal.applyConnectionSpec
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

class FirstFragment : Fragment() {

    var poster: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        getFirstData()
        view.basic_genre.isSelected = true
        return view
    }

    private fun getFirstData(){
        MovieClient.retrofitService.getMovie("kmdb_new2", "Y", "1QS3HYA074P8X6W4TEF3", "",
                "" + activity?.detail_title!!.text, "", "극영화", "1")
                .enqueue(object : retrofit2.Callback<MovieBase>{
                    override fun onResponse(call: Call<MovieBase>, response: Response<MovieBase>) {
                        val res = response.body()?.Data?.get(0)?.Result?.get(0)

                        basic_plot.setMovementMethod(ScrollingMovementMethod())

                        basic_genre.text = res?.genre                           // 영화장르
                        basic_summary_country.text = res?.nation                // 제작국가
                        basic_summary_run.text = res?.runtime+"분"              // 상영시간

                            if (res?.repRlsDate == "") {                        // 개봉날짜
                               basic_open.text = "알수없음"
                            } else {
                                basic_open.text = res!!.repRlsDate
                            }

                        basic_plot.text = res?.plots?.plot?.get(0)?.plotText    // 영롸내용,줄거리

                        // --------이미지넣기---------------------------------------------------------------------------------------------

                        var postIdx = res.posters.indexOf("|")
                        if(postIdx == -1){
                            postIdx = res.posters.length
                        }
                        var poster = if(res.posters.isNotEmpty()) res.posters.substring(0, postIdx) else ""
                        Glide.with(context!!)
                                .load(poster)
                                .into(basic_poster)
                        }
                        //---------------------------------------------------------------------------------------------------------------

                    override fun onFailure(call: Call<MovieBase>, t: Throwable) {
                            Log.d("Logd", t.message.toString())
                    }

                })
    }
}