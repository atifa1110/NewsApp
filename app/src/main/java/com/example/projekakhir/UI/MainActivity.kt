package com.example.projekakhir.UI

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.projekakhir.Adapter.TabPagerAdapter
import com.example.projekakhir.Database.NewsViewModel
import com.example.projekakhir.News
import com.example.projekakhir.R
import com.example.projekakhir.Util.Constant.Companion.ENTERTAINMENT
import com.example.projekakhir.Util.Constant.Companion.HEALTH
import com.example.projekakhir.Util.Constant.Companion.HOME
import com.example.projekakhir.Util.Constant.Companion.SCIENCE
import com.example.projekakhir.Util.Constant.Companion.SPORTS
import com.example.projekakhir.Util.Constant.Companion.TECHNOLOGY
import com.example.projekakhir.Util.Constant.Companion.TOTAL_NEWS_TAB
import com.example.projekakhir.databinding.ActivityMainBinding
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding

    private val TAB_TITLES = intArrayOf(
        R.string.tab_home,
        R.string.tab_sports,
        R.string.tab_health,
        R.string.tab_science,
        R.string.tab_entertainment,
        R.string.tab_technology
    )

    companion object {
        var EXTRADATA = "news"
        var homeNews: ArrayList<News> = ArrayList()
        var entertainmentNews: MutableList<News> = mutableListOf()
        var healthNews: MutableList<News> = mutableListOf()
        var scienceNews: MutableList<News> = mutableListOf()
        var sportsNews: MutableList<News> = mutableListOf()
        var techNews: MutableList<News> = mutableListOf()
        var apiRequestError = false
        var errorMessage = "error"
    }

    private lateinit var viewModel: NewsViewModel
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var tabAdapter: TabPagerAdapter
    private lateinit var shimmerLayout: ShimmerFrameLayout
    private var totalRequestCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar)

        tabAdapter = TabPagerAdapter(this)
        viewPager = findViewById(R.id.view_pager)
        binding?.viewPager?.adapter = tabAdapter
        tabLayout = findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        if (!viewModel.isNetworkAvailable(applicationContext)) {
            binding?.shimmerLayout?.visibility = View.GONE
            binding?.displayError?.text = getString(R.string.internet_warming)
            binding?.displayError?.visibility = View.VISIBLE
        }

        // Send request call for news data
        getNewsCategory(HOME, homeNews)
        getNewsCategory(SPORTS, sportsNews)
        getNewsCategory(HEALTH, healthNews)
        getNewsCategory(SCIENCE, scienceNews)
        getNewsCategory(TECHNOLOGY, techNews)
        getNewsCategory(ENTERTAINMENT, entertainmentNews)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        intent = Intent(applicationContext, BookmarkNewsActivity::class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }

    private fun getNewsCategory(newsCategory: String, newsData: MutableList<News>) {
        viewModel.getNewsApi(category = newsCategory)?.observe(this) {
            newsData.addAll(it)
            totalRequestCount += 1

            //If main fragment loaded then attach the fragment to viewPager
            if (newsCategory == HOME) {
                binding?.shimmerLayout?.stopShimmer()
                binding?.shimmerLayout?.hideShimmer()
                binding?.shimmerLayout?.visibility = View.GONE
                setViewPager()
            }

            if (totalRequestCount == TOTAL_NEWS_TAB) {
                viewPager.offscreenPageLimit = 6
            }
        }
    }

    private fun setViewPager() {
        if (!apiRequestError) {
            binding?.viewPager?.visibility = View.VISIBLE
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        } else {
            binding?.displayError?.text = errorMessage
            binding?.displayError?.visibility = View.VISIBLE
        }
    }
}

