package com.dicoding.storyappsubmission.ui.setting

import com.dicoding.storyappsubmission.data.repository.AuthRepository
import com.dicoding.storyappsubmission.utils.CoroutineTestRule
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
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SettingViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Mock
    private lateinit var authRepository: AuthRepository

    private lateinit var settingViewModel: SettingViewModel

    private val dummyToken = "authentication_token"
    private val dummyEmail = "aaa@gmail.com"
    private val dummyName = "rivan"

    @Before
    fun setup() {
        settingViewModel = SettingViewModel(authRepository)
    }

    @Test
    fun `Save authentication token successfully`(): Unit = runTest {
        settingViewModel.saveAuthToken(dummyToken)
        Mockito.verify(authRepository).saveAuthToken(dummyToken)
    }

    @Test
    fun `Save Auth profile successfully`(): Unit = runTest {
        settingViewModel.saveAuthProfile(dummyEmail, dummyName)
        Mockito.verify(authRepository).saveAuthProfile(dummyEmail, dummyName)
    }
}