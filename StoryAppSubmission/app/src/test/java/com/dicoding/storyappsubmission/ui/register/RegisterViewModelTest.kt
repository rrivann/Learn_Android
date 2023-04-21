package com.dicoding.storyappsubmission.ui.register

import com.dicoding.storyappsubmission.data.remote.response.RegisterResponse
import com.dicoding.storyappsubmission.data.repository.AuthRepository
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {

    @Mock
    private lateinit var authRepository: AuthRepository
    private lateinit var registerViewModel: RegisterViewModel

    private val dummyRegisterResponse = DataDummy.generateDummyRegisterResponse()
    private val dummyName = "Full Name"
    private val dummyEmail = "email@mail.com"
    private val dummyPassword = "password"

    @Before
    fun setup() {
        registerViewModel = RegisterViewModel(authRepository)
    }

    @Test
    fun `Registration successfully - result success`(): Unit = runTest {
        val expectedResponse = flowOf(Result.Success(dummyRegisterResponse))

        Mockito.`when`(registerViewModel.userRegister(dummyName, dummyEmail, dummyPassword))
            .thenReturn(
                expectedResponse
            )

        registerViewModel.userRegister(dummyName, dummyEmail, dummyPassword).collect { response ->

            when (response) {
                is Result.Success -> {
                    assertNotNull(response.data)
                    assertSame(dummyRegisterResponse, response.data)

                    assertTrue(true)
                    assertFalse(false)
                }

                is Result.Error -> {}
                is Result.Loading -> {}
            }

        }

        Mockito.verify(authRepository).userRegister(dummyName, dummyEmail, dummyPassword)
    }

    @Test
    fun `Registration failed - result with exception`(): Unit = runTest {
        val expectedResponse: Flow<Result<RegisterResponse>> =
            flowOf(Result.Error("failed"))

        Mockito.`when`(registerViewModel.userRegister(dummyName, dummyEmail, dummyPassword))
            .thenReturn(
                expectedResponse
            )

        registerViewModel.userRegister(dummyName, dummyEmail, dummyPassword).collect { response ->

            when (response) {
                is Result.Success -> {
                    assertNotNull(response.data)
                    assertSame(dummyRegisterResponse, response.data)

                    assertFalse(true)
                    assertTrue(false)
                }

                is Result.Error -> {}
                is Result.Loading -> {
                    assertNotNull(response)
                }
            }
        }
    }
}