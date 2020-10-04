package com.hiroshisasmita.newsapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hiroshisasmita.newsapp.R
import com.hiroshisasmita.newsapp.model.Article
import com.hiroshisasmita.newsapp.model.base.BaseRecyclerViewPagingAdapter
import com.hiroshisasmita.newsapp.util.DateParser
import com.hiroshisasmita.newsapp.util.extention.getProgressDrawable
import com.hiroshisasmita.newsapp.util.extention.loadImage
import kotlinx.android.synthetic.main.item_news.view.*

class NewsListAdapter: BaseRecyclerViewPagingAdapter<Article, RecyclerView.ViewHolder>() {
    private lateinit var mContext: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun setUpView(holder: RecyclerView.ViewHolder, item: Article) {
        holder.itemView.imageView.loadImage(item.urlToImage, getProgressDrawable(mContext))
        holder.itemView.itemTitle.text = item.title
        holder.itemView.itemCategory.text = "Technology"
        if (item.publishedAt != null)
            holder.itemView.itemPublishTime.text = DateParser.convertDate(item.publishedAt)

        holder.itemView.setOnClickListener {
            notifyItemClicked(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }
}