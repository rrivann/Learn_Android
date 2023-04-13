package com.dicoding.storyappsubmission.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.storyappsubmission.database.local.entity.StoryEntity
import com.dicoding.storyappsubmission.database.local.room.StoryDatabase
import com.dicoding.storyappsubmission.database.remote.api.ApiService
import com.dicoding.storyappsubmission.database.remote.response.AddStoryResponse
import com.dicoding.storyappsubmission.database.remote.response.DetailStoryResponse
import com.dicoding.storyappsubmission.database.remote.response.GetAllStoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import com.dicoding.storyappsubmission.utils.Result

class StoryRepository @Inject constructor(
    private val apiService: ApiService,
    private val storyDatabase: StoryDatabase
) {

    fun getAllStoriesOnlineToOffline(token: String): LiveData<Result<GetAllStoryResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val bearerToken = generateBearerToken(token)
                val response = apiService.getAllStory(bearerToken)
                response.listStory.forEach { storyResponseItem ->
                    val story = StoryEntity(
                        storyResponseItem.id,
                        storyResponseItem.name,
                        storyResponseItem.description,
                        storyResponseItem.createdAt,
                        storyResponseItem.photoUrl,
                        storyResponseItem.lon,
                        storyResponseItem.lat
                    )
                    storyDatabase.storyDao().insertStory(story)
                }
                emit(Result.Success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(e.toString()))
            }
        }

    fun getDetailStory(token: String, id: String): LiveData<Result<DetailStoryResponse>> =
        liveData {
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
    ): LiveData<Result<AddStoryResponse>> = liveData {
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