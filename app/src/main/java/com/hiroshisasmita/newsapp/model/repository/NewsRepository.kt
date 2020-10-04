package com.hiroshisasmita.newsapp.model.repository

import android.content.SharedPreferences
import com.hiroshisasmita.newsapp.model.base.BaseRepository
import com.hiroshisasmita.newsapp.model.response.NewsResponse
import com.hiroshisasmita.newsapp.model.retrofit.NewsService
import com.hiroshisasmita.newsapp.util.Const
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NewsRepository @Inject constructor(service: NewsService, preferences: SharedPreferences) : BaseRepository(service, preferences) {
    companion object {
        const val pageSize = 5
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
                calculatePage(it)
                true
            }
            .toSingle()
    }

    private fun calculatePage(newsResponse: NewsResponse): NewsResponse{
        var totalPage = 0
        if (newsResponse.totalResults != null) {
            totalPage = newsResponse.totalResults/pageSize
            if (newsResponse.totalResults%pageSize != 0) totalPage++
        }
        newsResponse.totalPage = totalPage
        return newsResponse
    }

}