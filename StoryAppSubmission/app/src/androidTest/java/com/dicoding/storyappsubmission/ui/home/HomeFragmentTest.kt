package com.dicoding.storyappsubmission.ui.home

import androidx.paging.ExperimentalPagingApi
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.matcher.ViewMatchers.withId
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import com.dicoding.storyappsubmission.R
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import com.dicoding.storyappsubmission.ui.home.utils.JsonConverter
import com.dicoding.storyappsubmission.ui.home.utils.launchFragmentInHiltContainer
import com.dicoding.storyappsubmission.utils.EspressoIdlingResource
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dicoding.storyappsubmission.data.remote.api.ApiConfig.Companion.BASE_URL_MOCK
import org.junit.runner.RunWith

@MediumTest
@ExperimentalPagingApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockWebServer.start(8080)
        BASE_URL_MOCK = "http://127.0.0.1:8080/"
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun launchHomeFragment_Success() {
        launchFragmentInHiltContainer<HomeFragment>()

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("success_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.rvStory)).check(matches(isDisplayed()))

        onView(withText("rivan")).check(matches(isDisplayed()))
    }

    @Test
    fun launchHomeFragment_Empty() {
        launchFragmentInHiltContainer<HomeFragment>()

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("success_response_empty.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.iv_not_found_error)).check(matches(isDisplayed()))
    }

    @Test
    fun launchHomeFragment_Failed() {
        launchFragmentInHiltContainer<HomeFragment>()

        val mockResponse = MockResponse()
            .setResponseCode(500)
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.iv_not_found_error)).check(matches(isDisplayed()))
    }

}