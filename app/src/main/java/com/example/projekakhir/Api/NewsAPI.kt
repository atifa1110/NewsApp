package com.example.projekakhir.Api

import com.example.projekakhir.Database.Source

data class NewsAPI (
    val author: String,
    val description: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)
