package com.hiroshisasmita.newsapp.model.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {

    protected abstract fun onNoInternetCallback(): () -> Unit

    protected fun showNoInternetError(){
        // TODO: 03/10/2020
    }
}