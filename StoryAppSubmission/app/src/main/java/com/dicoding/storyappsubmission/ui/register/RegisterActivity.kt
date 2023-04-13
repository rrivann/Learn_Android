package com.dicoding.storyappsubmission.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.storyappsubmission.R
import com.dicoding.storyappsubmission.databinding.ActivityRegisterBinding
import com.dicoding.storyappsubmission.ui.login.LoginActivity
import com.dicoding.storyappsubmission.utils.setupViewFullScreen
import dagger.hilt.android.AndroidEntryPoint
import com.dicoding.storyappsubmission.utils.Result
import com.dicoding.storyappsubmission.utils.showLoading


@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewFullScreen(window, supportActionBar)

        binding.registerButton.setOnClickListener { handleRegister() }
        playAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun handleRegister() {
        val name = binding.nameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()


        registerViewModel.userRegister(name, email, password).observe(this) { result ->
            if (result != null) when (result) {
                is Result.Loading -> {
                    showLoading(true, binding.progressBar)
                }
                is Result.Success -> {
                    showLoading(false, binding.progressBar)
                    Toast.makeText(this, R.string.registration_success, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is Result.Error -> {
                    showLoading(false, binding.progressBar)
                    Toast.makeText(this, R.string.registration_error_message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun playAnimation() {

        val title = ObjectAnimator.ofFloat(binding.titleRegister, View.ALPHA, 1f).setDuration(500)
        val desc = ObjectAnimator.ofFloat(binding.descRegister, View.ALPHA, 1f).setDuration(500)
        val name = ObjectAnimator.ofFloat(binding.nameEditText, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(500)
        val password =
            ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(500)
        val button =
            ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title, desc, name, email, password, button)
            startDelay = 500
            start()
        }
    }


}