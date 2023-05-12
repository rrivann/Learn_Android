package com.dicoding.myreactivesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.myreactivesearch.network.ApiConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest

@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModel : ViewModel() {

    private val accessToken = "pk.eyJ1IjoicnJpdmFubiIsImEiOiJjbGd2cHBxOXEycDFyM3RsdWltbGlqMjhpIn0.d_Fv8LQVz_XD1dqyLgl7XQ"
    val queryChannel = MutableStateFlow("")

    val searchResult = queryChannel
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .mapLatest {
            ApiConfig.provideApiService().getCountry(it, accessToken).features
        }
        .asLiveData()
}
