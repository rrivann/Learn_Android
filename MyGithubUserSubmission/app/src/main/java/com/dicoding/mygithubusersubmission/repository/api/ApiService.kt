package com.dicoding.mygithubusersubmission.repository.api

import com.dicoding.mygithubusersubmission.repository.response.DetailUserResponse
import com.dicoding.mygithubusersubmission.repository.response.GithubUserResponse
import com.dicoding.mygithubusersubmission.repository.response.ListFollowerUserResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getSearchUser(@Query("q") q: String?): Call<GithubUserResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String?): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowersUser(@Path("username") username: String?): Call<List<ListFollowerUserResponseData>>

    @GET("users/{username}/following")
    fun getFollowingUser(@Path("username") username: String?): Call<List<ListFollowerUserResponseData>>
}