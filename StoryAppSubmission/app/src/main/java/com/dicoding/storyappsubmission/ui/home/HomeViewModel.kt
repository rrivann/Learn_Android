package com.dicoding.storyappsubmission.ui.home

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.dicoding.storyappsubmission.data.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class HomeViewModel @Inject constructor(
    private val storyRepository: StoryRepository?
) : ViewModel() {

    fun getAllStories(token: String) =
        storyRepository?.getAllStoriesOnlineToOffline(token)?.cachedIn(viewModelScope)
}