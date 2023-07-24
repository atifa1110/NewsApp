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
import com.example.projekakhir.databinding.ActivityMainBinding
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding

    private val TAB_TITLES = intArrayOf(
        R.string.tab_home, R.string.tab_sports,
        R.string.tab_health, R.string.tab_science,
        R.string.tab_entertainment, R.string.tab_technology
    )

    companion object {
        var apiRequestError = false
        var errorMessage = "error"
    }

    private lateinit var viewModel: NewsViewModel
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var tabAdapter: TabPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar)
        supportActionBar?.elevation = 0f

        tabAdapter = TabPagerAdapter(this)
        viewPager = binding?.viewPager!!
        binding?.viewPager?.adapter = tabAdapter
        tabLayout = findViewById(R.id.tab_layout)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        if (!viewModel.isNetworkAvailable(applicationContext)) {
            binding?.displayError?.text = getString(R.string.internet_warming)
            binding?.displayError?.visibility = View.VISIBLE
        }

        setViewPager();
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menu?.findItem(R.id.bookmark_delete_all)?.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        intent = Intent(applicationContext, BookmarkActivity::class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
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

