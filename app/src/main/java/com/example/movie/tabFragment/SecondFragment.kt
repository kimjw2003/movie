package com.example.movie.tabFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.R
import com.example.movie.adapter.ActorAdapter
import com.example.movie.adapter.RcViewAdapter
import com.example.movie.data.ActorData
import com.example.movie.data.MovieBase
import com.example.movie.retrofit.MovieClient
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_second.*
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
                .enqueue(object : retrofit2.Callback<MovieBase>{
                    override fun onResponse(call: Call<MovieBase>, response: Response<MovieBase>) {
                        var res = response.body()?.Data?.get(0)?.Result?.get(0)

                        director_name.text = res?.directors?.director?.get(0)?.directorNm
                        director_engname.text = res?.directors?.director?.get(0)?.directorEnNm

                        var actorList = ArrayList<ActorData>()
                        actorList.add(ActorData(res?.actors?.actor?.get(0)?.actorNm.toString()))

                        val adapter = ActorAdapter(actorList)
                        actor_rcView.adapter = adapter
                        actor_rcView.layoutManager = LinearLayoutManager(context).also { it.orientation = LinearLayoutManager.HORIZONTAL }
                    }

                    override fun onFailure(call: Call<MovieBase>, t: Throwable) {
                        Log.d("Logd", t.message.toString())
                    }

                })
    }
}