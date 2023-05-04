package com.dicoding.mylocationtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Intent(this, LocationTrackerActivity::class.java).also { intent ->
            startActivity(intent)
            finish()
        }
    }
}