package com.testcase.newsapp.model.base

import android.content.SharedPreferences
import com.testcase.newsapp.model.retrofit.NewsService
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import java.lang.Exception

abstract class BaseRepository(protected val service: NewsService, protected val preferences: SharedPreferences) {

    protected open fun <T> validateServiceCall(single: Single<Response<T>>): Single<T> {
        return single.flatMap {
            if (it.code() != 200 || it.body() == null || !it.isSuccessful) {
                throw Exception("Error on Internal Server")
            }
            Single.just(it.body())
        }
    }
}