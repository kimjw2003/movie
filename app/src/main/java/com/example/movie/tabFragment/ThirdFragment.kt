package com.example.movie.tabFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.R
import com.example.movie.adapter.HornerRcViewAdapter
import com.example.movie.data.Base
import com.example.movie.data.HornerData
import com.example.movie.retrofit.MovieClient
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_third.*
import retrofit2.Call
import retrofit2.Response

class ThirdFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_third, container, false)

            getAwards()

        return view
    }

    private fun getAwards(){
        MovieClient.retrofitService.getMovie("kmdb_new2", "Y", "1QS3HYA074P8X6W4TEF3", "",
                "" + activity?.detail_title!!.text, "", "극영화", "1")
                .enqueue(object : retrofit2.Callback<Base>{
                    override fun onResponse(call: Call<Base>, response: Response<Base>) {
                        val res = response.body()?.Data?.get(0)?.Result?.get(0)

                        val hornerList = ArrayList<HornerData>()
                            response.body()?.Data?.get(0)?.Result?.forEach { it ->
                                var hornerIdx = it.Awards1.split("|").map { HornerData(it) }
                                hornerList.addAll(hornerIdx)
                            }

                        val adapter = HornerRcViewAdapter(hornerList)
                        horner_rcView.adapter = adapter
                        horner_rcView.layoutManager = LinearLayoutManager(context)

                    }
                    override fun onFailure(call: Call<Base>, t: Throwable) {
                        Log.d("Logd", t.message.toString())
                    }
                })
    }
}