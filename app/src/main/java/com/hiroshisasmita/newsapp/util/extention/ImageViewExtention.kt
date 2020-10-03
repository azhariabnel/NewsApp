package com.hiroshisasmita.newsapp.util.extention

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable?) {
    Glide.with(context)
        .setDefaultRequestOptions(getRequestOptions(progressDrawable))
        .load(uri)
        .transition(DrawableTransitionOptions.withCrossFade(200))
        .into(this)
}

fun getRequestOptions(progressDrawable: CircularProgressDrawable?): RequestOptions{
    return RequestOptions()
        .placeholder(progressDrawable)
        .centerCrop()
        .fitCenter()
}

fun getProgressDrawable(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}