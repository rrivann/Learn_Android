package com.dicoding.mylivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.dicoding.mylivedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribe()
    }

    private fun subscribe() {
        val elapsedTimeObserver = Observer<Long?> {
            val newText = this@MainActivity.resources.getString(R.string.seconds, it)
            binding.timerTextView.text = newText
        }

        viewModel.getElapsedTime().observe(this, elapsedTimeObserver)
    }
}