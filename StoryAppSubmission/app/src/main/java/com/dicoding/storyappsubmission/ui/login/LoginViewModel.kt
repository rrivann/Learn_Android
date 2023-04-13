package com.dicoding.storyappsubmission.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.storyappsubmission.database.remote.response.LoginResponse
import com.dicoding.storyappsubmission.repository.AuthRepository
import javax.inject.Inject
import com.dicoding.storyappsubmission.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    fun userLogin(email: String, password: String): LiveData<Result<LoginResponse>> =
        authRepository.userLogin(email, password)

    fun saveAuthToken(token: String) {
        viewModelScope.launch {
            authRepository.saveAuthToken(token)
        }
    }

    fun saveAuthProfile(email: String, name: String) {
        viewModelScope.launch {
            authRepository.saveAuthProfile(email, name)
        }
    }
}