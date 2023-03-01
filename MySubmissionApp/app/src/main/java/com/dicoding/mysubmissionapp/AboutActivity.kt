package com.dicoding.mysubmissionapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.mysubmissionapp.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.tvNameAbout.text = getString(R.string.my_name)
        binding.tvEmailAbout.text = getString(R.string.my_email)
    }
}