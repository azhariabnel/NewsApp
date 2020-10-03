package com.hiroshisasmita.newsapp.model.base

import com.hiroshisasmita.newsapp.model.retrofit.NewsService
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import java.lang.Exception

abstract class BaseRepository(protected val service: NewsService) {

    protected open fun <T> validateServiceCall(single: Single<Response<T>>): Single<T> {
        return single.flatMap {
            if (it.code() != 200 || it.body() == null || !it.isSuccessful) {
                throw Exception("Internal Server Error")
            }
            Single.just(it.body())
        }
    }
}