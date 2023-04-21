package com.dicoding.storyappsubmission.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.storyappsubmission.data.local.preferences.AuthPreferences
import com.dicoding.storyappsubmission.data.local.preferences.ProfilePreferences
import com.dicoding.storyappsubmission.data.remote.api.ApiService
import com.dicoding.storyappsubmission.data.remote.response.LoginResponse
import com.dicoding.storyappsubmission.data.remote.response.RegisterResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.dicoding.storyappsubmission.utils.Result
import kotlinx.coroutines.flow.flow

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences,
    private val profilePreferences: ProfilePreferences
) {

    fun userLogin(email: String, password: String): Flow<Result<LoginResponse>> =
        flow {
            emit(Result.Loading)
            try {
                val response = apiService.login(email, password)
                emit(Result.Success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(e.toString()))
            }
        }

    fun userRegister(
        name: String,
        email: String,
        password: String
    ): Flow<Result<RegisterResponse>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.register(name, email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.toString()))
        }
    }

    suspend fun saveAuthToken(token: String) = authPreferences.saveAuthToken(token)

    fun getAuthToken(): Flow<String?> = authPreferences.getAuthToken()

    suspend fun saveAuthProfile(email: String, name: String) =
        profilePreferences.saveAuthProfile(email, name)

    fun getAuthEmail(): Flow<String?> = profilePreferences.getAuthEmail()

    fun getAuthName(): Flow<String?> = profilePreferences.getAuthName()
}