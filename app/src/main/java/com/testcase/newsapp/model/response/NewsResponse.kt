package com.testcase.newsapp.model.response

import com.testcase.newsapp.model.Article

data class NewsResponse (
    val status: String?,
    val code: String?,
    val message: String?,
    val page: Int?,
    var totalPage: Int?,
    val totalResults: Int?,
    val articles: MutableList<Article>?
)