package com.app.mediaplayer.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.io.File

@BindingAdapter("path")
fun loadimage(view : ImageView, url : String){
    Glide.with(view).load(File(url)).into(view)
}