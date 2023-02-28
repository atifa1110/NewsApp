package com.example.projekakhir.Database

import com.example.projekakhir.News
import com.google.gson.annotations.SerializedName

data class ResponseNews(
    val status : String,
    val totalResults : Int,
    val articles: MutableList<NewsAPI>
)
