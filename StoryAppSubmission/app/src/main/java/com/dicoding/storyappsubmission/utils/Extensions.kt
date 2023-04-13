package com.dicoding.storyappsubmission.utils

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.dicoding.storyappsubmission.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.setImageFromUrl(context: Context, url: String) {
    Glide
        .with(context)
        .load(url)
        .placeholder(R.drawable.image_loading_placeholder)
        .error(R.drawable.image_load_error)
        .into(this)
}

fun TextView.setLocalDateFormat(timestamp: String) {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    val date = sdf.parse(timestamp) as Date

    val formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date)
    this.text = formattedDate
}