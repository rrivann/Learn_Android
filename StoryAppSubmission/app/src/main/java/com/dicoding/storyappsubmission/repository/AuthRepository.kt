package com.dicoding.storyappsubmission.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.storyappsubmission.database.local.AuthPreferences
import com.dicoding.storyappsubmission.database.local.ProfilePreferences
import com.dicoding.storyappsubmission.database.remote.api.ApiService
import com.dicoding.storyappsubmission.database.remote.response.LoginResponse
import com.dicoding.storyappsubmission.database.remote.response.RegisterResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.dicoding.storyappsubmission.utils.Result

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences,
    private val profilePreferences: ProfilePreferences
) {

    fun userLogin(email: String, password: String): LiveData<Result<LoginResponse>> =
        liveData {
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
    ): LiveData<Result<RegisterResponse>> = liveData {
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