package com.example.movie.retrofit

import com.example.movie.data.Base
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieDao {
    @GET("search_json2.jsp")
    fun getMovie(
        @Query("collection") collection : String,
        @Query("detail") detail : String,
        @Query("ServiceKey") ServiceKey : String,
        @Query("nation") nation : String,
        @Query("releaseDts") releaseDts : String
    ): Call<Base>
}