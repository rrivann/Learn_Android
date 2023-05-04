package com.dicoding.storyappsubmission.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.storyappsubmission.databinding.ActivityMainBinding
import com.dicoding.storyappsubmission.utils.setupViewFullScreen
import com.dicoding.storyappsubmission.ui.login.LoginActivity
import com.dicoding.storyappsubmission.ui.register.RegisterActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewFullScreen(window, supportActionBar)
        setupButton()
    }

    private fun setupButton() {
        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }


    companion object {
        const val EXTRA_TOKEN = "extra_token"
    }
}