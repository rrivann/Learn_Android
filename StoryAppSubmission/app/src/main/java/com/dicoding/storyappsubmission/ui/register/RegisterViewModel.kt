package com.dicoding.storyappsubmission.ui.register

import androidx.lifecycle.ViewModel
import com.dicoding.storyappsubmission.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    fun userRegister(name: String, email: String, password: String) =
        authRepository.userRegister(name, email, password)
}