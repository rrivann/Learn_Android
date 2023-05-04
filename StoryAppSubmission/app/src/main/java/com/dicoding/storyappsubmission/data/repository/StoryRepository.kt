package com.dicoding.storyappsubmission.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dicoding.storyappsubmission.data.local.entity.StoryEntity
import com.dicoding.storyappsubmission.data.local.room.StoryDatabase
import com.dicoding.storyappsubmission.data.remote.StoryRemoteMediator
import com.dicoding.storyappsubmission.data.remote.api.ApiService
import com.dicoding.storyappsubmission.data.remote.response.AddStoryResponse
import com.dicoding.storyappsubmission.data.remote.response.DetailStoryResponse
import com.dicoding.storyappsubmission.data.remote.response.GetAllStoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import com.dicoding.storyappsubmission.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalPagingApi
class StoryRepository @Inject constructor(
    private val apiService: ApiService,
    private val storyDatabase: StoryDatabase
) {

    fun getAllStoriesOnlineToOffline(token: String): Flow<PagingData<StoryEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(
                storyDatabase,
                apiService,
                generateBearerToken(token)
            ),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStories()
            }
        ).flow
    }

    fun getAllStoryWithLocation(token: String): Flow<Result<GetAllStoryResponse>> = flow {
        emit(Result.Loading)
        try {
            val bearerToken = generateBearerToken(token)
            val response = apiService.getAllStory(bearerToken, size = 30, location = 1)
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.toString()))
        }
    }

    fun getDetailStory(token: String, id: String): Flow<Result<DetailStoryResponse>> =
        flow {
            emit(Result.Loading)
            try {
                val bearerToken = generateBearerToken(token)
                val response = apiService.getDetailStory(bearerToken, id)
                emit(Result.Success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(e.toString()))
            }
        }

    fun addStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody? = null,
        lon: RequestBody? = null
    ): Flow<Result<AddStoryResponse>> = flow {
        emit(Result.Loading)
        try {
            val bearerToken = generateBearerToken(token)
            val response = apiService.addStory(bearerToken, file, description, lat, lon)
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.toString()))
        }
    }

    private fun generateBearerToken(token: String): String {
        return "Bearer $token"
    }
}