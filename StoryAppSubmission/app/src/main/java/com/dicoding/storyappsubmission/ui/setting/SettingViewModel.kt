package com.dicoding.storyappsubmission.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.storyappsubmission.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

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

    fun getAuthEmail(): Flow<String?> = authRepository.getAuthEmail()

    fun getAuthName(): Flow<String?> = authRepository.getAuthName()
}