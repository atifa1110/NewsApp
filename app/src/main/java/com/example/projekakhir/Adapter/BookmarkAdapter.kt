package com.example.projekakhir.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projekakhir.News
import com.example.projekakhir.R
import com.example.projekakhir.UI.DetailActivity
import com.example.projekakhir.Util.Constant
import com.example.projekakhir.databinding.ItemNewsBinding

public class BookmarkAdapter () : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    private lateinit var context: Context
    private var listBookmarkNews: List<News> = listOf()
    private lateinit var mLongClickListener: OnItemLongClickListener

    fun setNewsList(listNews: List<News>) {
        this.listBookmarkNews = listNews
        notifyDataSetChanged()
    }

    fun getPosition(position: Int): News {
        return listBookmarkNews.get(position)
    }

    fun setContext(context: Context) {
        this.context = context
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        mLongClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return BookmarkViewHolder(binding, context)
    }

    override fun getItemCount(): Int {
        return listBookmarkNews.size
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(mLongClickListener, listBookmarkNews[position])
    }

    class BookmarkViewHolder(private val binding: ItemNewsBinding, context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        var time: String? = ""

        fun bind(listener: OnItemLongClickListener, news: News) {
            with(binding) {
                newsTitle.text = news.title
                time = news.publishedAt;
                val date = " " + time?.substring(0, time!!.indexOf('T', 0))
                newsPublicationTime.text = com.example.projekakhir.Util.DateHelper.DateFormat(date)
                newsAuthor.text = news.author

                Glide.with(itemView.context)
                    .load(news.urlToImage)
                    .centerCrop()
                    .placeholder(com.example.projekakhir.R.drawable.news)
                    .thumbnail(0.5f)
                    .into(newsImage);

                cardNews.setOnLongClickListener {
                    listener.onItemLongClick(adapterPosition)
                    return@setOnLongClickListener true
                }

                cardNews.setOnClickListener {
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