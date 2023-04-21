package com.dicoding.storyappsubmission.ui.detailStory

import androidx.paging.ExperimentalPagingApi
import com.dicoding.storyappsubmission.data.remote.response.DetailStoryResponse
import com.dicoding.storyappsubmission.data.repository.AuthRepository
import com.dicoding.storyappsubmission.data.repository.StoryRepository
import com.dicoding.storyappsubmission.utils.DataDummy
import com.dicoding.storyappsubmission.utils.Result
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

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @Mock
    private lateinit var authRepository: AuthRepository

    @Mock
    private lateinit var storyRepository: StoryRepository

    private lateinit var detailViewModel: DetailViewModel

    private val dummyToken = "authentication_token"
    private val dummyId = "111"
    private val dummyDetailResponse = DataDummy.generateDetailStoryResponse()

    @Before
    fun setup() {
        detailViewModel = DetailViewModel(storyRepository, authRepository)
    }

    @Test
    fun `Get authentication token successfully`() = runTest {
        val expectedToken = flowOf(dummyToken)

        Mockito.`when`(detailViewModel.getAuthToken()).thenReturn(expectedToken)

        detailViewModel.getAuthToken().collect { actualToken ->
            assertNotNull(actualToken)
            assertEquals(dummyToken, actualToken)
        }

        Mockito.verify(authRepository).getAuthToken()
        Mockito.verifyNoInteractions(storyRepository)
    }

    @Test
    fun `Get authentication token successfully but null`() = runTest {
        val expectedToken = flowOf(null)

        Mockito.`when`(detailViewModel.getAuthToken()).thenReturn(expectedToken)

        detailViewModel.getAuthToken().collect { actualToken ->
            assertNull(actualToken)
        }

        Mockito.verify(authRepository).getAuthToken()
        Mockito.verifyNoInteractions(storyRepository)
    }


    @Test
    fun `Get Detail Story successfully`() = runTest {
        val expectedResponse = flowOf(Result.Success(dummyDetailResponse))

        Mockito.`when`(
            detailViewModel.getDetailStory(
                dummyToken, dummyId
            )
        ).thenReturn(expectedResponse)

        detailViewModel.getDetailStory(dummyToken, dummyId)
            .collect { result ->
                when (result) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        assertSame(dummyDetailResponse, result.data)
                        assertNotNull(result.data)
                        assertFalse(false)
                        assertTrue(true)
                    }

                    is Result.Error -> {}
                }
            }

        Mockito.verify(storyRepository)
            .getDetailStory(dummyToken, dummyId)
        Mockito.verifyNoInteractions(authRepository)
    }

    @Test
    fun `Get Detail Story failed`() = runTest {

        val expectedResponse: Flow<Result<DetailStoryResponse>> =
            flowOf(Result.Error("failed"))

        Mockito.`when`(
            detailViewModel.getDetailStory(
                dummyToken,
                dummyId
            )
        ).thenReturn(expectedResponse)

        detailViewModel.getDetailStory(dummyToken, dummyId)
            .collect { result ->
                when (result) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        assertFalse(true)
                        assertTrue(false)
                    }
                    is Result.Error -> {
                        assertNotNull(result.error)
                    }
                }
            }
    }
}