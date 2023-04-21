package com.dicoding.storyappsubmission.ui.register

import androidx.lifecycle.ViewModel
import com.dicoding.storyappsubmission.data.remote.response.RegisterResponse
import com.dicoding.storyappsubmission.data.repository.AuthRepository
import com.dicoding.storyappsubmission.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    fun userRegister(name: String, email: String, password: String): Flow<Result<RegisterResponse>> =
        authRepository.userRegister(name, email, password)
}