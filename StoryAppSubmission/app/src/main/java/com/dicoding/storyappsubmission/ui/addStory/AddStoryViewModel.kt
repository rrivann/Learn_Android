package com.dicoding.storyappsubmission.ui.addStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.storyappsubmission.database.remote.response.AddStoryResponse
import com.dicoding.storyappsubmission.repository.AuthRepository
import com.dicoding.storyappsubmission.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import com.dicoding.storyappsubmission.utils.Result

@HiltViewModel
class AddStoryViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    fun addStory(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ): LiveData<Result<AddStoryResponse>> =
        storyRepository.addStory(token, file, description, lat, lon)

     fun getAuthToken(): Flow<String?> = authRepository.getAuthToken()

}