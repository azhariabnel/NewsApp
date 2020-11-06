package com.testcase.newsapp.model.retrofit

import com.testcase.newsapp.model.response.NewsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines?category=technology")
    fun retrieveNews(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): Single<Response<NewsResponse>>
}