package com.example.rosty.githubusers.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso

@BindingAdapter("android:imageUrl")
fun loadImage(imageView: ImageView, link: String?) {

    if (link == null) return

    Picasso.with(imageView.context)
            .load(link)
            .into(imageView)
}