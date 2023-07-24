package com.example.projekakhir.Api

import com.example.projekakhir.Api.NewsAPI

data class ResponseNews(
    val status : String,
    val totalResults : Int,
    val articles: MutableList<NewsAPI>
)
