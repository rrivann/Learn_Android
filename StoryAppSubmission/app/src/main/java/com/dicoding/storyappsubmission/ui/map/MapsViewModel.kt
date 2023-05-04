package com.dicoding.storyappsubmission.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.dicoding.storyappsubmission.data.remote.response.GetAllStoryResponse
import com.dicoding.storyappsubmission.data.repository.StoryRepository
import com.dicoding.storyappsubmission.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class MapsViewModel @Inject constructor(private val storyRepository: StoryRepository) :
    ViewModel() {

    fun getAllStoryWithLocation(token: String): Flow<Result<GetAllStoryResponse>> =
        storyRepository.getAllStoryWithLocation(token)
}