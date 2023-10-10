package com.example.randogpic.util

import android.content.Context
import android.view.View
import android.widget.ImageView

interface ImageLoader {


    fun load(context: Context,url:String,imageView: ImageView)
}