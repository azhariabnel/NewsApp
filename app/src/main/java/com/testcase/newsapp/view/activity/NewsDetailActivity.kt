package com.testcase.newsapp.view.activity

import android.content.Intent
import android.view.View
import com.testcase.newsapp.R
import com.testcase.newsapp.model.Article
import com.testcase.newsapp.model.base.BaseActivity
import com.testcase.newsapp.util.Const
import com.testcase.newsapp.util.DateParser
import com.testcase.newsapp.util.extention.getProgressDrawable
import com.testcase.newsapp.util.extention.loadImage
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

    private fun setView() {
        ivArticle.loadImage(article?.urlToImage, getProgressDrawable(this))
        tvArticleTitle.text = article?.title
        if (article?.publishedAt != null) tvArticlePublishTime.text = DateParser.convertDate(article!!.publishedAt!!)
        tvArticleAuthor.text = article?.author
        tvArticleDesc.text = article?.description
        tvArticleContent.text = article?.content
        tvArticleUrl.text = article?.url
    }

    private fun getExtra(intent: Intent?) {
        article = intent?.getParcelableExtra(Const.ARTICLE_EXTRA)
    }

    override fun onClickToolbarComponent(view: View) {
        when (view.id){
            R.id.ivToolbarBackBtn -> onBackPressed()
        }
    }

    override fun onNoInternetRetryCallback(): (() -> Unit)? {
        return null
    }
}