package com.example.projekakhir.Api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    fun getNews(
        @Query("country") country: String,
        @Query("pageSize") pageSize : Int,
        @Query("apiKey") apiKey : String
    ): Call<ResponseNews>

    @GET("top-headlines")
    fun getCategoryNews(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("pageSize") pageSize : Int,
        @Query("apiKey") apiKey : String?
    ): Call<ResponseNews>

    @GET("everything")
    fun getNewsSearch(
        @Query("q") keyword: String?,
        @Query("language") language: String?,
        @Query("apiKey") apiKey: String?
    ): Call<ResponseNews>
}
