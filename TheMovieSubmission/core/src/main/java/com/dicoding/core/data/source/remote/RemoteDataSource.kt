package com.dicoding.core.data.source.remote

import android.util.Log
import com.dicoding.core.data.source.remote.network.ApiResponse
import com.dicoding.core.data.source.remote.network.ApiResponse.*
import com.dicoding.core.data.source.remote.network.ApiService
import com.dicoding.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.dicoding.core.BuildConfig.API_KEY

class RemoteDataSource(
    private val apiService: ApiService
) {
    suspend fun getAllMovie(): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                apiService.getNowPlayingMovie(API_KEY, 1).also {
                    it.results.apply {
                        if (isNotEmpty()) {
                            emit(Success(this))
                        } else {
                            emit(Empty)
                        }
                    }
                }
            } catch (e: Exception) {
                e.apply {
                    emit(Error(toString()))
                    Log.e("RemoteDataSource", e.message.toString())
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}