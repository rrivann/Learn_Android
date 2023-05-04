package com.dicoding.storyappsubmission.ui.map

import androidx.paging.ExperimentalPagingApi
import com.dicoding.storyappsubmission.data.remote.response.GetAllStoryResponse
import com.dicoding.storyappsubmission.data.repository.StoryRepository
import com.dicoding.storyappsubmission.utils.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import com.dicoding.storyappsubmission.utils.Result

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var locationViewModel: MapsViewModel

    private val dummyStoriesResponse = DataDummy.generateDummyStoryResponse()
    private val dummyToken = "AUTH_TOKEN"

    @Before
    fun setup() {
        locationViewModel = MapsViewModel(storyRepository)
    }

    @Test
    fun `Get story with location successfully - result success`(): Unit = runTest {

        val expectedResponse = flowOf(Result.Success(dummyStoriesResponse))

        Mockito.`when`(locationViewModel.getAllStoryWithLocation(dummyToken))
            .thenReturn(expectedResponse)

        locationViewModel.getAllStoryWithLocation(dummyToken).collect { actualResponse ->
            when (actualResponse) {
                is Result.Success -> {
                    assertNotNull(actualResponse.data)
                    assertSame(actualResponse.data, dummyStoriesResponse)

                    assertTrue(true)
                    assertFalse(false)
                }

                is Result.Error -> {}
                is Result.Loading -> {}
            }

        }

        Mockito.verify(storyRepository).getAllStoryWithLocation(dummyToken)
    }

    @Test
    fun `Get story with location failed - result failure with exception`(): Unit = runTest {

        val expectedResponse: Flow<Result<GetAllStoryResponse>> =
            flowOf(Result.Error("Failed"))

        Mockito.`when`(locationViewModel.getAllStoryWithLocation(dummyToken))
            .thenReturn(expectedResponse)

        locationViewModel.getAllStoryWithLocation(dummyToken).collect { actualResponse ->
            when (actualResponse) {
                is Result.Success -> {
                    assertTrue(false)
                    assertFalse(true)
                    assertNotNull(actualResponse.data)
                }
                is Result.Error -> {}

                is Result.Loading -> {}
            }
        }
        Mockito.verify(storyRepository).getAllStoryWithLocation(dummyToken)
    }
}