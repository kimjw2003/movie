package com.example.movie

import android.graphics.Color
import android.graphics.Movie
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.adapter.RcViewAdapter
import com.example.movie.data.MovieBase
import com.example.movie.data.MovieData
import com.example.movie.retrofit.MovieClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    var country : String? = null

    private var mBackWait: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        //스피너 값 내용
        var countryList = listOf("대한민국", "미국")

        // 스피너 값에따라 결과값 달라지게하기
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, countryList)
        country_Sp.adapter = adapter
        country_Sp.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedValue = countryList[position]
                country = selectedValue
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        // 리사이클러뷰 검색시에만 보이게하기

        search_Btn.setOnClickListener {
            progress.visibility = View.VISIBLE
            getMovie()
            rcView.visibility = View.VISIBLE

            item_size_title.setTextColor(Color.parseColor("#000000"))
        }

        // 확인 눌러도 검색하기
        search_Et.setOnEditorActionListener { v, actionId, event ->
            progress.visibility = View.VISIBLE
            getMovie()
            true
        }

    } //onCreate

    private fun getMovie(){
        MovieClient.retrofitService.getMovie("kmdb_new2", "Y", "1QS3HYA074P8X6W4TEF3", ""+country.toString(),
             ""+search_Et.text.toString(), "", "극영화", "500"
        ).enqueue(object : retrofit2.Callback<MovieBase> {
            override fun onResponse(call: Call<MovieBase>, response: Response<MovieBase>) {
                Log.d("Logd", "onResponse")
                Log.d("Logd","검색된 영화 수 : "+ response.body()?.TotalCount.toString())

                progress.visibility = View.GONE

                item_size.text = response.body()?.TotalCount.toString()
                if(item_size.text.toString().toInt() > 500){
                    item_size.text = "500(최대검색 건수)"
                }

                    val movieList = ArrayList<MovieData>()
                        response.body()?.Data?.get(0)?.Result?.forEach {it ->       //response.body()?.Data?.get(0)?.Result -> it 으로 정의
                            var postIdx = it.posters.indexOf("|")
                            if(postIdx == -1){
                                postIdx = it.posters.length
                            }
                            movieList.add(MovieData(it.titleEtc!!.substring(0, it.titleEtc.indexOf("^")),
                                it.actors?.actor?.get(0)?.actorNm!!,
                                if(it.posters.isNotEmpty()) it.posters.substring(0, postIdx) else "", country.toString()))
                    }

                    val adapter = RcViewAdapter(movieList)
                    rcView.adapter = adapter
                    rcView.layoutManager = LinearLayoutManager(this@SearchActivity)
            }

            override fun onFailure(call: Call<MovieBase>, t: Throwable) {
                Log.d("Logd", t.message.toString())
            }
        })
    }

    override fun onBackPressed() {
        // 뒤로가기 버튼 클릭
        if (System.currentTimeMillis() - mBackWait >= 1500) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show() //토스트 출력
        } else {
            moveTaskToBack(true) //finish후 다른 액티비티가 보여지는것을 방지
            finish() //액티비티 종료
            android.os.Process.killProcess(android.os.Process.myPid()) //앱, 프로세스까지 강제종료
        }
    }
}