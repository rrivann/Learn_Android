package com.dicoding.mysubmissionapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Crypto(
    val name: String,
    val totalSupply: String,
    val description: String,
    val photo: String
) : Parcelable