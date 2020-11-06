package com.testcase.newsapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.testcase.newsapp.R
import com.testcase.newsapp.model.Article
import com.testcase.newsapp.model.base.InfiniteRecycleViewPagingAdapter
import com.testcase.newsapp.util.DateParser
import com.testcase.newsapp.util.extention.getProgressDrawable
import com.testcase.newsapp.util.extention.loadImage
import kotlinx.android.synthetic.main.item_news.view.*

class NewsListAdapter: InfiniteRecycleViewPagingAdapter<Article, RecyclerView.ViewHolder>() {

    private lateinit var mContext: Context
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun setUpView(holder: RecyclerView.ViewHolder, item: Article) {
        holder.itemView.ivNews.loadImage(item.urlToImage, getProgressDrawable(mContext))
        holder.itemView.tvNewsTitle.text = item.title
        holder.itemView.tvNewsCategory.text = "Technology"
        if (item.publishedAt != null)
            holder.itemView.tvNewsPublishTime.text = DateParser.convertDate(item.publishedAt)

        holder.itemView.setOnClickListener {
            notifyItemClicked(item)
        }
    }
}