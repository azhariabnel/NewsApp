package com.testcase.newsapp.model.repository

import android.content.SharedPreferences
import com.testcase.newsapp.model.base.BaseRepository
import com.testcase.newsapp.model.response.NewsResponse
import com.testcase.newsapp.model.retrofit.NewsService
import com.testcase.newsapp.util.Const
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NewsRepository @Inject constructor(service: NewsService, preferences: SharedPreferences) : BaseRepository(service, preferences) {
    companion object {
        const val pageSize = 5
    }

    private fun countPage(newsResponse: NewsResponse): NewsResponse{
        var totalPage = 0
        if (newsResponse.totalResults != null) {
            totalPage = newsResponse.totalResults/pageSize
            if (newsResponse.totalResults%pageSize != 0) totalPage++
        }
        newsResponse.totalPage = totalPage
        return newsResponse
    }

    fun retrieveNews(page: Int): Single<NewsResponse> {
        return validateServiceCall(
            service.retrieveNews(
                preferences.getString(Const.COUNTRY_PREFERENCE, "id")?:"id",
                page,
                pageSize
            )
        )
            .subscribeOn(Schedulers.io())
            .filter {
                countPage(it)
                true
            }
            .toSingle()
    }

}