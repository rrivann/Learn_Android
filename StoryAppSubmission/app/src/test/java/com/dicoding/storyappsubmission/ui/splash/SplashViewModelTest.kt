package com.dicoding.storyappsubmission.ui.splash

import com.dicoding.storyappsubmission.data.repository.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SplashViewModelTest {


    @Mock
    private lateinit var authRepository: AuthRepository
    private lateinit var splashViewModel: SplashViewModel

    private val dummyToken = "authentication_token"

    @Before
    fun setup() {
        splashViewModel = SplashViewModel(authRepository)
    }

    @Test
    fun `Get authentication token successfully`() = runTest {
        val expectedToken = flowOf(dummyToken)

        Mockito.`when`(splashViewModel.getAuthToken()).thenReturn(expectedToken)

        splashViewModel.getAuthToken().collect { actualToken ->
            assertNotNull(actualToken)
            assertEquals(dummyToken, actualToken)
        }

        Mockito.verify(authRepository).getAuthToken()
    }

    @Test
    fun `Get authentication token empty`() = runTest {
        val expectedToken = flowOf(null)

        Mockito.`when`(splashViewModel.getAuthToken()).thenReturn(expectedToken)

        splashViewModel.getAuthToken().collect { actualToken ->
            assertNull(actualToken)
        }

        Mockito.verify(authRepository).getAuthToken()
    }

}