package com.hiroshisasmita.newsapp.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hiroshisasmita.newsapp.model.repository.NewsRepository
import com.hiroshisasmita.newsapp.model.response.NewsResponse
import com.hiroshisasmita.newsapp.model.response.ResponseResult
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class NewsListViewModel @ViewModelInject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    private val responseResultLiveData = MutableLiveData<ResponseResult<NewsResponse>>()
    private val compositeDisposable = CompositeDisposable()

    fun getObservable(): MutableLiveData<ResponseResult<NewsResponse>> = responseResultLiveData

    fun retrieveNews(page: Int){
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