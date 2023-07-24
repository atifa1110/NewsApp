package com.example.projekakhir.Database

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projekakhir.News
import com.example.projekakhir.Repository.NewsRepository

class NewsViewModel () : ViewModel(){

    private var newsLiveData: MutableLiveData<List<News>>? = null

    //get news from API
    fun getNewsApi(category: String?): MutableLiveData<List<News>>? {
        newsLiveData = category.let { NewsRepository().getNewsApiCall(it) }
        return newsLiveData
    }

    var newsData: LiveData<List<News>>? = null

    fun insert(context: Context, news: News) {
        NewsRepository.insert(context, news)
    }

    fun delete(context: Context, news: News) {
        NewsRepository.delete(context, news)
    }

    fun getNewsDatabase(context: Context): LiveData<List<News>>? {
        newsData = NewsRepository.getAllNews(context)
        return newsData
    }

    fun deleteNewsDatabase(context: Context){
        NewsRepository.deleteAllNews(context)
    }

    // Check internet connection
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // For 29 api or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            // For below 29 api
            if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
                return true
            }
        }
        return false
    }
}