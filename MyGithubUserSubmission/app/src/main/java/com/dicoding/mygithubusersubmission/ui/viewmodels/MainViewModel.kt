package com.dicoding.mygithubusersubmission.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mygithubusersubmission.repository.api.ApiConfig
import com.dicoding.mygithubusersubmission.repository.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _githubUser = MutableLiveData<List<GithubUserData>>()
    val githubUser: LiveData<List<GithubUserData>> = _githubUser

    private val _detailUser = MutableLiveData<DetailUserResponse?>()
    val detailUser: LiveData<DetailUserResponse?> = _detailUser

    fun searchGithubUser(query: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getSearchUser(query)
        client.enqueue(object : Callback<GithubUserResponse> {
            override fun onResponse(
                call: Call<GithubUserResponse>,
                response: Response<GithubUserResponse>
            ) {
                _isLoading.value = false

                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    _githubUser.value = responseBody.githubUser
                } else {
                    Log.d(TAG, "onFailure:${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "OnFailure: ${t.message}")
            }
        })
    }

    fun getDetailUser(username: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {

            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _detailUser.value = responseBody
                } else {
                    Log.d(TAG, "onFailure Detail:${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "OnFailure Detail: ${t.message}")
            }
        })
    }
}