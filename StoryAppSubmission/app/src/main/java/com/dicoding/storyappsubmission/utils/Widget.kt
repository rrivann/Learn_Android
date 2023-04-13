package com.dicoding.storyappsubmission.utils

import android.view.View
import android.widget.ProgressBar


fun showLoading(isLoading: Boolean, progressBar: ProgressBar) {
    progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
}