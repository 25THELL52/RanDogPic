package com.example.randogpic.util

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide

open class ImageLoaderImpl :ImageLoader {
    override fun load(context: Context, url: String, imageView: ImageView) {

        Glide.with(context).load(url).into(imageView)
        EspressoIdlingResource.decrement()
        Log.d("ImageLoaderImpl","isIdleNow2() ${EspressoIdlingResource.getIdlingResource()?.isIdleNow}")

    }

}