package com.testcase.newsapp.util.extention

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.testcase.newsapp.R

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable?) {
    Glide.with(context)
        .setDefaultRequestOptions(getRequestOptions(progressDrawable))
        .load(uri)
        .timeout(2000)
        .transition(DrawableTransitionOptions.withCrossFade(200))
        .into(this)
}

fun getProgressDrawable(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun getRequestOptions(progressDrawable: CircularProgressDrawable?): RequestOptions{
    return RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_error_image)
        .centerCrop()
        .fitCenter()
}