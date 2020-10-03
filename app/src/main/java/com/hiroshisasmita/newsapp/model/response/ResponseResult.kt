package com.hiroshisasmita.newsapp.model.response

class ResponseResult<T>() {
    var data: T? = null
    var error: Throwable? = null

    constructor(data: T): this() {
        this.data = data
    }

    constructor(error: Throwable): this() {
        this.error = error
    }
}