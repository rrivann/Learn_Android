package com.dicoding.storyappsubmission.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.ListUpdateCallback
import com.dicoding.storyappsubmission.data.local.entity.StoryEntity
import com.dicoding.storyappsubmission.data.repository.StoryRepository
import com.dicoding.storyappsubmission.utils.CoroutineTestRule
import com.dicoding.storyappsubmission.utils.DataDummy
import com.dicoding.storyappsubmission.utils.PagedTestDataSource
import com.dicoding.storyappsubmission.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    private lateinit var homeViewModel: HomeViewModel
    private val dummyToken = "authentication_token"

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(storyRepository)
    }

    @Test
    fun `Get all story successfully`() = runTest {
        val dummyStories = DataDummy.generateDummyListStory()
        val data = PagedTestDataSource.snapshot(dummyStories)

        val stories = flowOf(data)

        `when`(storyRepository.getAllStoriesOnlineToOffline(dummyToken)).thenReturn(
            stories
        )

        val actualStories = homeViewModel.getAllStories(dummyToken)?.asLiveData()?.getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryListAdapter.DiffCallback,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = coroutinesTestRule.testDispatcher,
            workerDispatcher = Dispatchers.Main
        )
        if (actualStories != null) {
            differ.submitData(actualStories)
        }


        advanceUntilIdle()

        assertNotNull(differ.snapshot())
        assertEquals(dummyStories.size, differ.snapshot().size)
        assertEquals(dummyStories[0], differ.snapshot()[0])
    }

    @Test
    fun `Get all story is empty`() = runTest {
        val dummyStories = mutableListOf<StoryEntity>()
        val data = PagedTestDataSource.snapshot(dummyStories)

        val stories = flowOf(data)

        `when`(storyRepository.getAllStoriesOnlineToOffline(dummyToken)).thenReturn(
            stories
        )
        val actualStories = homeViewModel.getAllStories(dummyToken)?.asLiveData()?.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryListAdapter.DiffCallback,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = coroutinesTestRule.testDispatcher,
            workerDispatcher = Dispatchers.Main,
        )
        if (actualStories != null) {
            differ.submitData(actualStories)
        }

        assertNotNull(differ.snapshot())
        assertTrue("is true", differ.snapshot().isEmpty())
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}