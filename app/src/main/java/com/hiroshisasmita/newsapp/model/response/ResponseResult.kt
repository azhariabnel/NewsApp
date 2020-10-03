package com.hiroshisasmita.newsapp.model.response

import java.lang.Exception

class ResponseResult<T>() {
    private var data: T? = null
    private var error: Exception? = null

    constructor(data: T): this() {
        this.data = data
    }

    constructor(error: Exception): this() {
        this.error = error
    }
}