package com.example.projekakhir.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.projekakhir.News

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news : News)

    @Delete
    fun delete(news: News)

    @Query("SELECT * FROM News")
    fun getAllNews(): LiveData<List<News>>

    @Query("DELETE FROM News")
    fun deleteAllNews()
}