package com.hiroshisasmita.newsapp.model.response

import com.hiroshisasmita.newsapp.model.Article

data class NewsResponse (
    val status: String?,
    val code: String?,
    val message: String?,
    val page: Int?,
    var totalPage: Int?,
    val totalResults: Int?,
    val articles: MutableList<Article>?
)