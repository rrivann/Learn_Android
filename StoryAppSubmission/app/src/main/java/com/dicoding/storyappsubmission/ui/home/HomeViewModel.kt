package com.dicoding.storyappsubmission.ui.home

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.dicoding.storyappsubmission.data.local.entity.StoryEntity
import com.dicoding.storyappsubmission.data.local.room.StoryDao
import com.dicoding.storyappsubmission.data.remote.response.GetAllStoryResponse
import com.dicoding.storyappsubmission.data.repository.StoryRepository
import com.dicoding.storyappsubmission.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class HomeViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val storyDao: StoryDao
) : ViewModel() {

    fun getAllStories(token: String): LiveData<PagingData<StoryEntity>> =
        storyRepository.getAllStoriesOnlineToOffline(token).cachedIn(viewModelScope).asLiveData()
}