package com.testcase.newsapp.util.exception

import java.io.IOException

class NoConnectionException: IOException() {
    override val message: String?
        get() = "No Internet Connection"
}