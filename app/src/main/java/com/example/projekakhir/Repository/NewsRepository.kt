package com.example.projekakhir.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.projekakhir.Api.ApiConfig
import com.example.projekakhir.Database.NewsRoomDatabase
import com.example.projekakhir.Api.ResponseNews
import com.example.projekakhir.UI.MainActivity
import com.example.projekakhir.News
import com.example.projekakhir.Util.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository () {

    private val TAG = "News Api"
    companion object {

        private var newsDatabase: NewsRoomDatabase? = null

        private fun initializeDB(context: Context): NewsRoomDatabase {
            return NewsRoomDatabase.getDatabase(context)
        }

        fun insert(context: Context, news: News) {
            newsDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                newsDatabase!!.getNewsDao().insert(news)
            }
        }

        fun delete(context: Context, news: News) {
            newsDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                newsDatabase!!.getNewsDao().delete(news)
            }
        }

        fun getAllNews(context: Context): LiveData<List<News>> {
            newsDatabase = initializeDB(context)
            return newsDatabase!!.getNewsDao().getAllNews()
        }

        fun deleteAllNews(context: Context){
            newsDatabase = initializeDB(context)
            return newsDatabase!!.getNewsDao().deleteAllNews()
        }

    }

    fun getNewsApiCall(category: String?): MutableLiveData<List<News>> {
        val newsList = MutableLiveData<List<News>>()
        val client = ApiConfig.getApiService().getCategoryNews(Constant.COUNTRY, category, 50, Constant.API_KEY)

        client.enqueue(object : Callback<ResponseNews> {
            override fun onResponse(call: Call<ResponseNews>, response: Response<ResponseNews>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    val tempNewsList = mutableListOf<News>()
                    responseBody.articles.forEach {
                        tempNewsList.add(
                            News( it.author, it.title,
                                  it.url, it.urlToImage,
                                  it.publishedAt, it.content )
                        )
                    }
                    newsList.value = tempNewsList
                    Log.i(TAG,"Berhasil")
                } else {
                    val jsonObj: JSONObject?
                    Log.i(TAG,"Gagal")
                    try {
                        jsonObj = response.errorBody()?.string()?.let { JSONObject(it) }
                        if (jsonObj != null) {
                            MainActivity.apiRequestError = true
                            MainActivity.errorMessage = jsonObj.getString("message")
                            val tempNewsList = mutableListOf<News>()
                            newsList.value = tempNewsList
                        }
                    } catch (e: JSONException) {
                        Log.d("JSONException", "" + e.message)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseNews>, t: Throwable) {
                MainActivity.apiRequestError = true
                MainActivity.errorMessage = t.localizedMessage as String
                Log.d("err_msg", "msg" + t.localizedMessage)
            }
        })
        return newsList
    }
}