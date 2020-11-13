package com.example.movie.tabFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.data.Base
import com.example.movie.retrofit.MovieClient
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*
import okhttp3.internal.applyConnectionSpec
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

class FirstFragment : Fragment() {

    var poster: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        getFirstData()
        return view
    }

    private fun getFirstData(){
        MovieClient.retrofitService.getMovie("kmdb_new2", "Y", "1QS3HYA074P8X6W4TEF3", "",
                "" + activity?.detail_title!!.text, "", "극영화", "1")
                .enqueue(object : retrofit2.Callback<Base>{
                    override fun onResponse(call: Call<Base>, response: Response<Base>) {
                        val res = response.body()?.Data?.get(0)?.Result?.get(0)

                        basic_genre.text = res?.genre
                        basic_summary_country.text = res?.nation
                        basic_summary_run.text = res?.runtime+"분"
                            if (res?.repRlsDate == "") {
                                basic_open.text = "알수없음"
                            } else {
                                basic_open.text = res!!.repRlsDate
                            }

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

                    override fun onFailure(call: Call<Base>, t: Throwable) {
                            Log.d("Logd", t.message.toString())
                    }

                })
    }
}