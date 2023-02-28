package com.example.projekakhir

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "News")
data class News(
    @PrimaryKey (autoGenerate = true)

    @ColumnInfo(name = "author")
    val author : String?,

    @ColumnInfo(name = "title")
    val title : String?,

    @ColumnInfo(name = "url")
    val url : String?,

    @ColumnInfo(name = "urlToImage")
    val urlToImage : String?,

    @ColumnInfo(name = "publishedAt")
    val publishedAt : String?,

    @ColumnInfo(name = "content")
    val content: String?
)
