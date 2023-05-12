package com.dicoding.core.data.source.remote.network

import com.dicoding.core.data.source.remote.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("now_playing")
    suspend fun getNowPlayingMovie(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): ListMovieResponse
}