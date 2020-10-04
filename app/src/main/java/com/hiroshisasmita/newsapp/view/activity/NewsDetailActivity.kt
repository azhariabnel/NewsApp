package com.hiroshisasmita.newsapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.hiroshisasmita.newsapp.R
import com.hiroshisasmita.newsapp.model.Article
import com.hiroshisasmita.newsapp.model.base.BaseActivity
import com.hiroshisasmita.newsapp.util.Const
import com.hiroshisasmita.newsapp.util.extention.getProgressDrawable
import com.hiroshisasmita.newsapp.util.extention.loadImage
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetailActivity : BaseActivity() {
    private var article: Article? = null

    override fun providesLayout(): Int {
        return R.layout.activity_news_detail
    }

    override fun onViewCreated() {
        getExtra(intent)
        setView()
        setSupportBackButton(true)
        setToolbarTitle("News Detail")
    }

    override fun onNoInternetRetryCallback(): (() -> Unit)? {
        //no internet connection needed in this activity
        return null
    }

    private fun setView() {
        articleImage.loadImage(article?.urlToImage, getProgressDrawable(this))
        articleTitle.text = article?.title
        articlePublishTime.text = article?.publishedAt
        articleAuthor.text = article?.author
        articleDescription.text = article?.description
        articleContent.text = article?.content
        articleUrl.text = article?.url
    }

    private fun getExtra(intent: Intent?) {
        article = intent?.getParcelableExtra(Const.ARTICLE_EXTRA)
    }

    override fun onClickToolbarComponent(view: View) {
        when (view.id){
            R.id.toolbarBackButton -> onBackPressed()
        }
    }
}