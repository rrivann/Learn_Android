package com.dicoding.mygithubusersubmission.repository.response

import com.google.gson.annotations.SerializedName

data class GithubUserResponse(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val githubUser: List<GithubUserData>
)

data class GithubUserData(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,
)
