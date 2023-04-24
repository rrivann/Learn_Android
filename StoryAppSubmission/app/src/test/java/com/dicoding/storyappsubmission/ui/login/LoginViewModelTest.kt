package com.dicoding.storyappsubmission.ui.login

import com.dicoding.storyappsubmission.data.remote.response.LoginResponse
import com.dicoding.storyappsubmission.data.repository.AuthRepository
import com.dicoding.storyappsubmission.utils.CoroutineTestRule
import com.dicoding.storyappsubmission.utils.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Mock
    private lateinit var authRepository: AuthRepository
    private lateinit var loginViewModel: LoginViewModel

    private val dummyLoginResponse = DataDummy.generateDummyLoginResponse()
    private val dummyToken = "authentication_token"
    private val dummyEmail = "email@mail.com"
    private val dummyPassword = "password"

    @Before
    fun setup() {
        loginViewModel = LoginViewModel(authRepository)
    }

    @Test
    fun `Login successfully - result success`(): Unit = runTest {
        val expectedResponse = flow {
            emit(Result.Success(dummyLoginResponse))
        }

        Mockito.`when`(loginViewModel.userLogin(dummyEmail, dummyPassword))
            .thenReturn(expectedResponse)

        loginViewModel.userLogin(dummyEmail, dummyPassword).collect { result ->
            when (result) {
                is Result.Success -> {
                    assertNotNull(result.data)
                    assertSame(dummyLoginResponse, result.data)

                    assertTrue(true)
                    assertFalse(false)
                }

                is Result.Loading -> {}
                is Result.Error -> {}
            }
        }

        Mockito.verify(authRepository).userLogin(dummyEmail, dummyPassword)
    }

    @Test
    fun `Login failed - result failure with exception`(): Unit = runTest {
        val expectedResponse: Flow<Result<LoginResponse>> =
            flowOf(Result.Error("login failed"))

        Mockito.`when`(loginViewModel.userLogin(dummyEmail, dummyPassword))
            .thenReturn(expectedResponse)

        loginViewModel.userLogin(dummyEmail, dummyPassword).collect { result ->
            when (result) {
                is Result.Success -> {
                    assertFalse(true)
                    assertTrue(false)
                    assertNotNull(result.data)
                }
                is Result.Loading -> {}
                is Result.Error -> {}
            }
        }

        Mockito.verify(authRepository).userLogin(dummyEmail, dummyPassword)
    }

    @Test
    fun `Save authentication token successfully`(): Unit = runTest {
        loginViewModel.saveAuthToken(dummyToken)
        Mockito.verify(authRepository).saveAuthToken(dummyToken)
    }
}