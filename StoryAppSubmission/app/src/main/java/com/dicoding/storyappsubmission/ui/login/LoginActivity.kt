package com.dicoding.storyappsubmission.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.dicoding.storyappsubmission.R
import com.dicoding.storyappsubmission.databinding.ActivityLoginBinding
import com.dicoding.storyappsubmission.ui.home.HomeActivity
import com.dicoding.storyappsubmission.ui.home.HomeActivity.Companion.EXTRA_TOKEN
import com.dicoding.storyappsubmission.utils.Result
import com.dicoding.storyappsubmission.utils.setupViewFullScreen
import com.dicoding.storyappsubmission.utils.showLoading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewFullScreen(window, supportActionBar)

        binding.loginButton.setOnClickListener { handleLogin() }
        playAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun handleLogin() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        lifecycleScope.launch {

            loginViewModel.userLogin(email, password).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        showLoading(true, binding.progressBar)
                    }

                    is Result.Success -> {
                        showLoading(false, binding.progressBar)
                        Toast.makeText(
                            this@LoginActivity,
                            R.string.login_success_message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        result.data.loginResult?.token?.let { token ->
                            loginViewModel.saveAuthToken(token)
                            result.data.loginResult.name?.let {
                                loginViewModel.saveAuthProfile(
                                    email,
                                    it
                                )
                            }
                            Intent(this@LoginActivity, HomeActivity::class.java).also { intent ->
                                intent.putExtra(EXTRA_TOKEN, token)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }

                    is Result.Error -> {
                        showLoading(false, binding.progressBar)
                        Toast.makeText(
                            this@LoginActivity,
                            R.string.login_error_message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        }
    }

    private fun playAnimation() {

        val title = ObjectAnimator.ofFloat(binding.titleLogin, View.ALPHA, 1f).setDuration(500)
        val desc = ObjectAnimator.ofFloat(binding.descLogin, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(500)
        val password =
            ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(500)
        val button =
            ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title, desc, email, password, button)
            startDelay = 500
            start()
        }
    }


}