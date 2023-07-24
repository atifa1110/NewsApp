package com.example.projekakhir.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projekakhir.Adapter.NewsAdapter
import com.example.projekakhir.Database.NewsViewModel
import com.example.projekakhir.News
import com.example.projekakhir.R
import com.example.projekakhir.UI.MainActivity
import com.example.projekakhir.Util.Constant
import com.facebook.shimmer.ShimmerFrameLayout

class TechnoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewModel: NewsViewModel
    private lateinit var shimmer: ShimmerFrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_techno, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shimmer = view.findViewById(R.id.shimmer_techno)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        viewModel.getNewsApi(category = "Technology")?.observe(viewLifecycleOwner, Observer {
            newsAdapter.setNewsList(it);
            if(newsAdapter.itemCount!=0){
                shimmer.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }else{
                shimmer.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
        });

        recyclerView = view.findViewById(R.id.rv_news)
        recyclerView.layoutManager = layoutManager
        newsAdapter = NewsAdapter()
        recyclerView.adapter = newsAdapter
    }

}