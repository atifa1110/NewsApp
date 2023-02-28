package com.example.projekakhir.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projekakhir.UI.DetailActivity
import com.example.projekakhir.News
import com.example.projekakhir.R
import com.example.projekakhir.UI.MainActivity
import com.example.projekakhir.Util.Constant
import com.example.projekakhir.databinding.ItemNewsBinding

class NewsAdapter (private var listNews: List<News>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(listNews[position])
    }

    class NewsViewHolder (private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root){
        var time : String? = ""
        fun bind(news : News) {
            with(binding) {
                newsTitle.text = news.title
                time = news.publishedAt;
                val date = " " + time?.substring(0, time!!.indexOf('T', 0))
                newsPublicationTime.text = date
                Glide.with(itemView.context)
                    .load(news.urlToImage)
                    .centerCrop()
                    .placeholder(R.drawable.news)
                    .thumbnail(0.5f)
                    .into(newsImage);

                cardImage.setOnClickListener {
                    val intent = Intent(it.context, DetailActivity::class.java)
                    intent.putExtra(Constant.NEWS_URL, news.url)
                    intent.putExtra(Constant.NEWS_TITLE, news.title)
                    intent.putExtra(Constant.NEWS_IMAGE_URL, news.urlToImage)
                    intent.putExtra(Constant.NEWS_PUBLICATION_TIME, news.publishedAt)
                    intent.putExtra(Constant.NEWS_AUTHOR, news.author)
                    intent.putExtra(Constant.NEWS_CONTENT, news.content)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}