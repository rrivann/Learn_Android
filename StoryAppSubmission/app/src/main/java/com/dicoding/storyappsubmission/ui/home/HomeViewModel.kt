package com.dicoding.storyappsubmission.ui.home

import androidx.lifecycle.*
import com.dicoding.storyappsubmission.database.local.entity.StoryEntity
import com.dicoding.storyappsubmission.database.local.room.StoryDao
import com.dicoding.storyappsubmission.database.remote.response.GetAllStoryResponse
import com.dicoding.storyappsubmission.repository.StoryRepository
import com.dicoding.storyappsubmission.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val storyDao: StoryDao
) : ViewModel() {

    fun getAllStories(token: String): LiveData<Result<GetAllStoryResponse>> =
        storyRepository.getAllStoriesOnlineToOffline(token)

    fun getListStoryDatabase(): LiveData<List<StoryEntity>> =
        storyDao.getAllStories()
}