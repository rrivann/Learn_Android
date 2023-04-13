package com.dicoding.storyappsubmission.ui.detailStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.storyappsubmission.database.remote.response.DetailStoryResponse
import com.dicoding.storyappsubmission.repository.AuthRepository
import com.dicoding.storyappsubmission.repository.StoryRepository
import com.dicoding.storyappsubmission.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    fun getDetailStory(token: String, id: String): LiveData<Result<DetailStoryResponse>> =
        storyRepository.getDetailStory(token, id)

    fun getAuthToken(): Flow<String?> = authRepository.getAuthToken()
}