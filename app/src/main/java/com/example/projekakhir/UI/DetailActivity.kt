package com.example.projekakhir.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import com.example.projekakhir.Database.NewsViewModel
import com.example.projekakhir.News
import com.example.projekakhir.R
import com.example.projekakhir.Util.Constant.Companion.NEWS
import com.example.projekakhir.Util.Constant.Companion.NEWS_AUTHOR
import com.example.projekakhir.Util.Constant.Companion.NEWS_CONTENT
import com.example.projekakhir.Util.Constant.Companion.NEWS_IMAGE_URL
import com.example.projekakhir.Util.Constant.Companion.NEWS_PUBLICATION_TIME
import com.example.projekakhir.Util.Constant.Companion.NEWS_TITLE
import com.example.projekakhir.Util.Constant.Companion.NEWS_URL

class DetailActivity : AppCompatActivity() {

    private lateinit var newsWebView: WebView
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsData: ArrayList<News>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        newsWebView = findViewById(R.id.news_webview)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        newsData = ArrayList(1)
        val newsUrl = intent.getStringExtra(NEWS_URL)
        val newsContent = intent.getStringExtra(NEWS_CONTENT) + ". get paid version to hear full news. "
        newsData.add(
            News(
                intent.getStringExtra(NEWS_AUTHOR),
                intent.getStringExtra(NEWS_TITLE)!!,
                newsUrl,
                intent.getStringExtra(NEWS_IMAGE_URL),
                intent.getStringExtra(NEWS_PUBLICATION_TIME),
                newsContent)
        )

        //Webview
        newsWebView.apply {
            settings.apply {
                domStorageEnabled = true
                loadsImagesAutomatically = true
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                javaScriptEnabled = true
            }
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
        }


        if (newsUrl != null) {
            newsWebView.loadUrl(newsUrl)
        }
    }
}