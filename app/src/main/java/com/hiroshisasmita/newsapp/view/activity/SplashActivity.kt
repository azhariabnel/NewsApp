package com.hiroshisasmita.newsapp.view.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.hiroshisasmita.newsapp.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        hideStatusBar()
        delayUiSplash()
    }

    private fun hideStatusBar() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    private fun delayUiSplash() {
        Handler(Looper.getMainLooper())
            .postDelayed(Runnable {
                intentToNewsListActivity()
            },3000)
    }

    private fun intentToNewsListActivity(){
        Intent(this, NewsListActivity::class.java).also {
            startActivity(it)
        }
        finish()
    }
}