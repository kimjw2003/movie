package com.example.movie.tabFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movie.R
import com.example.movie.data.Base
import com.example.movie.retrofit.MovieClient
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Response


class SecondFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)

        getPeople()

        return view
    }

    private fun getPeople(){
        MovieClient.retrofitService.getMovie("kmdb_new2", "Y", "1QS3HYA074P8X6W4TEF3", "",
                "" + activity?.detail_title!!.text, "", "극영화", "1")
                .enqueue(object : retrofit2.Callback<Base>{
                    override fun onResponse(call: Call<Base>, response: Response<Base>) {

                    }

                    override fun onFailure(call: Call<Base>, t: Throwable) {

                    }

                })
    }
}