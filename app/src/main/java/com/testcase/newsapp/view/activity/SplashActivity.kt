package com.testcase.newsapp.view.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import com.testcase.newsapp.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        hideTopBar()
        splashHandler()
    }

    private fun splashHandler() {
        Handler(Looper.getMainLooper())
            .postDelayed(Runnable {
                gotoNewsList()
            },3000)
    }

    private fun hideTopBar() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    private fun gotoNewsList(){
        Intent(this, NewsListActivity::class.java).also {
            startActivity(it)
        }
        finish()
    }
}