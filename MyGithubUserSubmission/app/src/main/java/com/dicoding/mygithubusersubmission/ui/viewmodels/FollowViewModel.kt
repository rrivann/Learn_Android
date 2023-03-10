package com.dicoding.mygithubusersubmission.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mygithubusersubmission.repository.api.ApiConfig
import com.dicoding.mygithubusersubmission.repository.response.ListFollowerUserResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    companion object {
        private const val TAG = "FollowViewModel"
    }

    private val _isLoadingFollow = MutableLiveData<Boolean>()
    val isLoadingFollow: LiveData<Boolean> = _isLoadingFollow

    private val _listFollowers = MutableLiveData<List<ListFollowerUserResponseData>?>()
    val listFollowers: LiveData<List<ListFollowerUserResponseData>?> = _listFollowers

    private val _listFollowing = MutableLiveData<List<ListFollowerUserResponseData>?>()
    val listFollowing: LiveData<List<ListFollowerUserResponseData>?> = _listFollowing

    fun getFollowersUser(username: String) {
        _isLoadingFollow.value = true
        val client = ApiConfig.getApiService().getFollowersUser(username)
        client.enqueue(object : Callback<List<ListFollowerUserResponseData>> {

            override fun onResponse(
                call: Call<List<ListFollowerUserResponseData>>,
                response: Response<List<ListFollowerUserResponseData>>
            ) {
                _isLoadingFollow.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _listFollowers.value = responseBody
                } else {
                    Log.d(TAG, "onFailure Follow:${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ListFollowerUserResponseData>>, t: Throwable) {
                _isLoadingFollow.value = false
                Log.e(TAG, "OnFailure Follow: ${t.message}")
            }
        })
    }


    fun getFollowingUser(username: String) {
        _isLoadingFollow.value = true
        val client = ApiConfig.getApiService().getFollowingUser(username)
        client.enqueue(object : Callback<List<ListFollowerUserResponseData>> {

            override fun onResponse(
                call: Call<List<ListFollowerUserResponseData>>,
                response: Response<List<ListFollowerUserResponseData>>
            ) {
                _isLoadingFollow.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _listFollowing.value = responseBody
                } else {
                    Log.d(TAG, "onFailure Follow:${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ListFollowerUserResponseData>>, t: Throwable) {
                _isLoadingFollow.value = false
                Log.e(TAG, "OnFailure Follow: ${t.message}")
            }
        })
    }


}