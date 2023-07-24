package com.example.projekakhir.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projekakhir.Adapter.BookmarkAdapter
import com.example.projekakhir.Adapter.NewsAdapter
import com.example.projekakhir.Database.NewsViewModel
import com.example.projekakhir.R
import com.example.projekakhir.Util.Constant
import com.example.projekakhir.databinding.ActivityBookmarkBinding
import com.facebook.shimmer.ShimmerFrameLayout
import okhttp3.internal.notify

class BookmarkActivity : AppCompatActivity() {

    private var _activityBookmarkBinding: ActivityBookmarkBinding? = null
    private val binding get() = _activityBookmarkBinding
    private lateinit var rvBookmark : RecyclerView
    private lateinit var bookmarkAdapter: BookmarkAdapter
    private lateinit var viewModel: NewsViewModel
    private lateinit var shimmer : ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityBookmarkBinding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar)
        supportActionBar?.elevation = 0f
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this@BookmarkActivity, LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        viewModel.getNewsDatabase(this@BookmarkActivity)?.observe(this){
            bookmarkAdapter.setNewsList(it)

            if(bookmarkAdapter.itemCount!=0){
                binding?.shimmerBookmark?.visibility = View.GONE
                rvBookmark.visibility = View.VISIBLE
            }else{
                binding?.shimmerBookmark?.visibility = View.VISIBLE
                rvBookmark.visibility = View.GONE
            }
        }

        rvBookmark= binding?.rvBookmark!!
        rvBookmark.layoutManager = layoutManager
        bookmarkAdapter = BookmarkAdapter()
        bookmarkAdapter.setContext(this@BookmarkActivity)

        bookmarkAdapter.setOnItemLongClickListener(object : BookmarkAdapter.OnItemLongClickListener{
            override fun onItemLongClick(position: Int) {
                val builder = AlertDialog.Builder(this@BookmarkActivity)
                builder.setTitle(Constant.BOOKMARK_TITLE)
                builder.setMessage(Constant.BOOKMARK_MESSAGE_DELETE)
                builder.setPositiveButton("Yes") { dialog, which ->
                    viewModel.delete(this@BookmarkActivity,bookmarkAdapter.getPosition(position))
                }
                builder.setNegativeButton("No"){ dialog, which ->

                }
                builder.show()
            }
        })

        rvBookmark.adapter = bookmarkAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menu?.findItem(R.id.bookmark_news)?.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //viewModel.deleteNewsDatabase(this@BookmarkActivity)
        return super.onOptionsItemSelected(item)
    }
}