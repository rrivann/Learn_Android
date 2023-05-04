package com.dicoding.storyappsubmission.ui.detailStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.dicoding.storyappsubmission.data.remote.response.DetailStoryResponse
import com.dicoding.storyappsubmission.data.repository.AuthRepository
import com.dicoding.storyappsubmission.data.repository.StoryRepository
import com.dicoding.storyappsubmission.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class DetailViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    fun getDetailStory(token: String, id: String): Flow<Result<DetailStoryResponse>> =
        storyRepository.getDetailStory(token, id)

    fun getAuthToken(): Flow<String?> = authRepository.getAuthToken()
}