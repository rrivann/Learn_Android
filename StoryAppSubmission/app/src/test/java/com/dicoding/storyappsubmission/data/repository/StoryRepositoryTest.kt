package com.dicoding.storyappsubmission.data.repository

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.ListUpdateCallback
import com.dicoding.storyappsubmission.data.local.room.StoryDatabase
import com.dicoding.storyappsubmission.data.remote.api.ApiService
import com.dicoding.storyappsubmission.data.remote.response.GetAllStoryResponse
import com.dicoding.storyappsubmission.ui.home.StoryListAdapter
import com.dicoding.storyappsubmission.utils.CoroutineTestRule
import com.dicoding.storyappsubmission.utils.DataDummy
import com.dicoding.storyappsubmission.utils.PagedTestDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import com.dicoding.storyappsubmission.utils.Result

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@RunWith(MockitoJUnitRunner::class)
class StoryRepositoryTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Mock
    private lateinit var storyDatabase: StoryDatabase

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var storyRepositoryMock: StoryRepository

    private lateinit var storyRepository: StoryRepository

    private val dummyToken = "authentication_token"
    private val dummyMultipart = DataDummy.generateDummyMultipartFile()
    private val dummyDescription = DataDummy.generateDummyRequestBody()
    private val dummyStoriesResponse = DataDummy.generateDummyStoryResponse()

    @Before
    fun setup() {
        storyRepository = StoryRepository(apiService, storyDatabase)
    }

    @Test
    fun `Get stories with pager - successfully`() = runTest {
        val dummyStories = DataDummy.generateDummyListStory()
        val data = PagedTestDataSource.snapshot(dummyStories)

        val expectedResult = flowOf(data)

        Mockito.`when`(storyRepositoryMock.getAllStoriesOnlineToOffline(dummyToken))
            .thenReturn(expectedResult)

        storyRepositoryMock.getAllStoriesOnlineToOffline(dummyToken).collect { actualResult ->
            val differ = AsyncPagingDataDiffer(
                diffCallback = StoryListAdapter.DiffCallback,
                updateCallback = noopListUpdateCallback,
                mainDispatcher = coroutinesTestRule.testDispatcher,
                workerDispatcher = coroutinesTestRule.testDispatcher
            )
            differ.submitData(actualResult)

            assertNotNull(differ.snapshot())
            assertEquals(
                dummyStoriesResponse.listStory.size,
                differ.snapshot().size
            )
        }

    }

    @Test
    fun `Get stories with location - successfully`() = runTest {
        val expectedResult = flowOf(Result.Success(dummyStoriesResponse))

        Mockito.`when`(storyRepositoryMock.getAllStoryWithLocation(dummyToken))
            .thenReturn(expectedResult)

        storyRepositoryMock.getAllStoryWithLocation(dummyToken).collect { result ->

            when (result) {
                is Result.Success -> {
                    assertNotNull(result.data)
                    assertEquals(dummyStoriesResponse, result.data)

                    assertTrue(true)
                    assertFalse(false)
                }
                is Result.Loading -> {}
                is Result.Error -> {}
            }
        }
    }

    @Test
    fun `Get stories with location - throw exception`() = runTest {
        val expectedResponse =
            flowOf<Result<GetAllStoryResponse>>(Result.Error("failed"))

        Mockito.`when`(storyRepositoryMock.getAllStoryWithLocation(dummyToken)).thenReturn(
            expectedResponse
        )

        storyRepositoryMock.getAllStoryWithLocation(dummyToken).collect { result ->
            when (result) {
                is Result.Success -> {
                    assertTrue(false)
                    assertFalse(true)
                }
                is Result.Loading -> {}
                is Result.Error -> {
                    assertNotNull(result.error)
                }
            }
        }
    }

    @Test
    fun `Add story file - successfully`() = runTest {
        val expectedResponse = DataDummy.generateDummyStoryUploadResponse()

        Mockito.`when`(
            apiService.addStory(
                dummyToken.generateBearerToken(),
                dummyMultipart,
                dummyDescription,
                null,
                null
            )
        ).thenReturn(expectedResponse)

        storyRepository.addStory(dummyToken, dummyMultipart, dummyDescription, null, null)
            .collect { result ->

                when (result) {
                    is Result.Success -> {
                        assertEquals(expectedResponse, result.data)

                        assertTrue(true)
                        assertFalse(false)
                    }
                    is Result.Loading -> {}
                    is Result.Error -> {}
                }
            }

        Mockito.verify(apiService)
            .addStory(
                dummyToken.generateBearerToken(),
                dummyMultipart,
                dummyDescription,
                null,
                null
            )
        Mockito.verifyNoInteractions(storyDatabase)
    }

    @Test
    fun `Upload image file - throw exception`() = runTest {

        Mockito.`when`(
            apiService.addStory(
                dummyToken.generateBearerToken(),
                dummyMultipart,
                dummyDescription,
                null,
                null
            )
        ).then { throw Exception() }

        storyRepository.addStory(dummyToken, dummyMultipart, dummyDescription, null, null)
            .collect { result ->

                when (result) {
                    is Result.Success -> {
                        assertTrue(false)
                        assertFalse(true)
                    }
                    is Result.Loading -> {}
                    is Result.Error -> {
                        assertNotNull(result.error)
                    }
                }
            }

        Mockito.verify(apiService).addStory(
            dummyToken.generateBearerToken(),
            dummyMultipart,
            dummyDescription,
            null,
            null
        )
    }

    private fun String.generateBearerToken(): String {
        return "Bearer $this"
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}