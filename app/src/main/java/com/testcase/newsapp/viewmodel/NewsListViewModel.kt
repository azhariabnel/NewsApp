package com.testcase.newsapp.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testcase.newsapp.model.repository.NewsRepository
import com.testcase.newsapp.model.response.NewsResponse
import com.testcase.newsapp.model.response.ResponseResult
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class NewsListViewModel @ViewModelInject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val responseResultLiveData = MutableLiveData<ResponseResult<NewsResponse>>()
    private val compositeDisposable = CompositeDisposable()

    fun getObservable(): MutableLiveData<ResponseResult<NewsResponse>> = responseResultLiveData

    fun getNews(page: Int){
        compositeDisposable.add(
            repository.retrieveNews(page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        responseResultLiveData.value = ResponseResult(it)
                    },
                    {
                        responseResultLiveData.value = ResponseResult(it)
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}