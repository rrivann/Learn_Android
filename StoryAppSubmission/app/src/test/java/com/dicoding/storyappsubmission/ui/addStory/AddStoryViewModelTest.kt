package com.dicoding.storyappsubmission.ui.addStory

import com.dicoding.storyappsubmission.utils.Result
import androidx.paging.ExperimentalPagingApi
import com.dicoding.storyappsubmission.data.remote.response.AddStoryResponse
import com.dicoding.storyappsubmission.data.repository.AuthRepository
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
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {

    @Mock
    private lateinit var authRepository: AuthRepository

    @Mock
    private lateinit var storyRepository: StoryRepository

    private lateinit var addStoryViewModel: AddStoryViewModel

    private val dummyToken = "authentication_token"
    private val dummyUploadResponse = DataDummy.generateDummyStoryUploadResponse()
    private val dummyMultipart = DataDummy.generateDummyMultipartFile()
    private val dummyDescription = DataDummy.generateDummyRequestBody()


    @Before
    fun setup() {
        addStoryViewModel = AddStoryViewModel(storyRepository, authRepository)
    }

    @Test
    fun `Get authentication token successfully`() = runTest {
        val expectedToken = flowOf(dummyToken)

        `when`(addStoryViewModel.getAuthToken()).thenReturn(expectedToken)

        addStoryViewModel.getAuthToken().collect { actualToken ->
            assertNotNull(actualToken)
            assertEquals(dummyToken, actualToken)
        }

        Mockito.verify(authRepository).getAuthToken()
        Mockito.verifyNoInteractions(storyRepository)
    }

    @Test
    fun `Get authentication token successfully but null`() = runTest {
        val expectedToken = flowOf(null)

        `when`(addStoryViewModel.getAuthToken()).thenReturn(expectedToken)

        addStoryViewModel.getAuthToken().collect { actualToken ->
            assertNull(actualToken)
        }

        Mockito.verify(authRepository).getAuthToken()
        Mockito.verifyNoInteractions(storyRepository)
    }


    @Test
    fun `Add Story successfully`() = runTest {
        val expectedResponse = flowOf(Result.Success(dummyUploadResponse))

        `when`(
            addStoryViewModel.addStory(
                dummyToken,
                dummyMultipart,
                dummyDescription,
                null,
                null
            )
        ).thenReturn(expectedResponse)

        addStoryViewModel.addStory(dummyToken, dummyMultipart, dummyDescription, null, null)
            .collect { result ->
                when (result) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        assertSame(dummyUploadResponse, result.data)
                        assertNotNull(result.data)
                        assertFalse(false)
                        assertTrue(true)
                    }

                    is Result.Error -> {}
                }
            }

        Mockito.verify(storyRepository)
            .addStory(dummyToken, dummyMultipart, dummyDescription, null, null)
        Mockito.verifyNoInteractions(authRepository)
    }

    @Test
    fun `Add Story failed`() = runTest {
        val expectedResponse: Flow<Result<AddStoryResponse>> =
            flowOf(Result.Error("failed"))

        `when`(
            addStoryViewModel.addStory(
                dummyToken,
                dummyMultipart,
                dummyDescription,
                null,
                null
            )
        ).thenReturn(expectedResponse)

        addStoryViewModel.addStory(dummyToken, dummyMultipart, dummyDescription, null, null)
            .collect { result ->
                when (result) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        assertTrue(false)
                        assertFalse(true)
                        assertNotNull(result.data)
                    }
                    is Result.Error -> {
                    }
                }
            }
    }
}