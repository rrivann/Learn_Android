package com.dicoding.storyappsubmission.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.dicoding.storyappsubmission.ui.home.HomeActivity
import com.dicoding.storyappsubmission.ui.home.HomeActivity.Companion.EXTRA_TOKEN
import com.dicoding.storyappsubmission.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        determineUserDirection()
    }

    private fun determineUserDirection() {
        lifecycleScope.launch {
            delay(2000)
            viewModel.getAuthToken().collect { token ->
                if (token.isNullOrEmpty()) {
                    Intent(this@SplashActivity, MainActivity::class.java).also { intent ->
                        startActivity(intent)
                        finish()
                    }
                } else {
                    Intent(this@SplashActivity, HomeActivity::class.java).also { intent ->
                        intent.putExtra(EXTRA_TOKEN, token)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}