package com.example.movie.tabFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.movie.R
import com.example.movie.data.Base
import com.example.movie.retrofit.MovieClient
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

class FirstFragment : Fragment() {

    var title: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)



        return view
    }

    private fun getFirstData(){
        MovieClient.retrofitService.getMovie("kmdb_new2", "Y", "1QS3HYA074P8X6W4TEF3", "",
                ""+detail_title.text.toString(), "", "극영화", "1")
                .enqueue(object : retrofit2.Callback<Base>{
                    override fun onResponse(call: Call<Base>, response: Response<Base>) {
                        val res = response.body()?.Data?.get(0)?.Result?.get(0)


                    }

                    override fun onFailure(call: Call<Base>, t: Throwable) {

                    }

                })
    }
}