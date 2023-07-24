package com.example.projekakhir.UI

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.projekakhir.Database.NewsViewModel
import com.example.projekakhir.News
import com.example.projekakhir.Util.Constant
import com.example.projekakhir.Util.Constant.Companion.NEWS_AUTHOR
import com.example.projekakhir.Util.Constant.Companion.NEWS_CONTENT
import com.example.projekakhir.Util.Constant.Companion.NEWS_IMAGE_URL
import com.example.projekakhir.Util.Constant.Companion.NEWS_PUBLICATION_TIME
import com.example.projekakhir.Util.Constant.Companion.NEWS_TITLE
import com.example.projekakhir.Util.Constant.Companion.NEWS_URL
import com.example.projekakhir.databinding.ActivityDetailBinding
import com.example.projekakhir.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailActivity : AppCompatActivity() {

    private var _activityDetailBinding: ActivityDetailBinding? = null
    private val binding get() = _activityDetailBinding

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsData: ArrayList<News>
    private lateinit var toolbar: MaterialToolbar
    private lateinit var bookmarkNews: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar)
        assert(supportActionBar != null)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

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
        binding?.newswebview?.apply {
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
            binding?.newswebview?.loadUrl(newsUrl)
        }

        bookmarkNews = binding?.floatingActionButton!!
        bookmarkNews.setOnClickListener(View.OnClickListener {
            createDialog()
        });

    }

    fun createDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(Constant.BOOKMARK_TITLE)
        builder.setMessage(Constant.BOOKMARK_MESSAGE_INSERT)
        builder.setPositiveButton("Yes") { dialog, which ->
            viewModel.insert(this@DetailActivity, newsData[0])
            Toast.makeText(this@DetailActivity,"Added Successfully",Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }
}