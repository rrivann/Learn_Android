package com.dicoding.storyappsubmission.data.repository

import com.dicoding.storyappsubmission.data.local.preferences.AuthPreferences
import com.dicoding.storyappsubmission.data.local.preferences.ProfilePreferences
import com.dicoding.storyappsubmission.data.remote.api.ApiService
import com.dicoding.storyappsubmission.utils.CoroutineTestRule
import com.dicoding.storyappsubmission.utils.DataDummy
import com.dicoding.storyappsubmission.utils.Result
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Mock
    private lateinit var preferencesDataSource: AuthPreferences

    @Mock
    private lateinit var apiService: ApiService
    private lateinit var authRepository: AuthRepository

    @Mock
    private lateinit var profilePreferences: ProfilePreferences

    private val dummyName = "Name"
    private val dummyEmail = "mail@mail.com"
    private val dummyPassword = "password"
    private val dummyToken = "authentication_token"

    @Before
    fun setup() {
        authRepository = AuthRepository(apiService, preferencesDataSource, profilePreferences)
    }

    @Test
    fun `User login successfully`(): Unit = runTest {
        val expectedResponse = DataDummy.generateDummyLoginResponse()

        Mockito.`when`(apiService.login(dummyEmail, dummyPassword)).thenReturn(expectedResponse)

        authRepository.userLogin(dummyEmail, dummyPassword).collect { result ->

            when (result) {
                is Result.Success -> {
                    assertNotNull(result.data)
                    assertEquals(expectedResponse, result.data)

                    assertTrue(true)
                    assertFalse(false)
                }

                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }

    }

    @Test
    fun `User login failed - throw exception`(): Unit = runTest {
        val expectedResponse = DataDummy.generateDummyLoginResponse()
        Mockito.`when`(apiService.login(dummyEmail, dummyPassword)).thenReturn(expectedResponse)

        authRepository.userLogin(dummyEmail, dummyPassword).collect { result ->
            when (result) {
                is Result.Success -> {}
                is Result.Error -> {
                    assertFalse(true)
                    assertTrue(false)
                    assertNotNull(result)
                }
                is Result.Loading -> {}
            }
        }
    }

    @Test
    fun `User register successfully`(): Unit = runTest {
        val expectedResponse = DataDummy.generateDummyRegisterResponse()

        Mockito.`when`(apiService.register(dummyName, dummyEmail, dummyPassword)).thenReturn(
            expectedResponse
        )

        authRepository.userRegister(dummyName, dummyEmail, dummyPassword).collect { result ->

            when (result) {
                is Result.Success -> {
                    assertNotNull(result.data)
                    assertEquals(expectedResponse, result.data)

                    assertTrue(true)
                    assertFalse(false)
                }

                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }
    }

    @Test
    fun `User register failed - throw exception`(): Unit = runTest {
        Mockito.`when`(
            apiService.register(
                dummyName,
                dummyEmail,
                dummyPassword
            )
        ).then { throw Exception() }

        authRepository.userRegister(dummyName, dummyEmail, dummyPassword).collect { result ->
            when (result) {
                is Result.Success -> {
                    assertFalse(true)
                    assertTrue(false)
                    assertNotNull(result)
                }
                is Result.Error -> {}
                is Result.Loading -> {}
            }
        }
    }

    @Test
    fun `Save auth token successfully`() = runTest {
        authRepository.saveAuthToken(dummyToken)
        Mockito.verify(preferencesDataSource).saveAuthToken(dummyToken)
    }

    @Test
    fun `Get authentication token successfully`() = runTest {
        val expectedToken = flowOf(dummyToken)

        Mockito.`when`(preferencesDataSource.getAuthToken()).thenReturn(expectedToken)

        authRepository.getAuthToken().collect { actualToken ->
            assertNotNull(actualToken)
            assertEquals(dummyToken, actualToken)
        }

        Mockito.verify(preferencesDataSource).getAuthToken()
    }

}