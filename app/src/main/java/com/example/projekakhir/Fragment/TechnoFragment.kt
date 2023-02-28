package com.example.projekakhir.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projekakhir.Adapter.NewsAdapter
import com.example.projekakhir.News
import com.example.projekakhir.R
import com.example.projekakhir.UI.MainActivity
import com.example.projekakhir.Util.Constant

class TechnoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var newsList: List<News>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_techno, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = view.findViewById(R.id.rv_news)
        recyclerView.layoutManager = layoutManager

        // Setting recyclerViews adapter
        newsList = MainActivity.techNews.slice(Constant.TOP_HEADLINES_COUNT until MainActivity.techNews.size - Constant.TOP_HEADLINES_COUNT)
        adapter = NewsAdapter(newsList)
        recyclerView.adapter = adapter
    }

}