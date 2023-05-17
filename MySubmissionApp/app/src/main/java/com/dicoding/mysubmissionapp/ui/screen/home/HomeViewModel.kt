package com.dicoding.mysubmissionapp.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mysubmissionapp.core.data.CryptoRepository
import com.dicoding.mysubmissionapp.core.model.Crypto
import com.dicoding.mysubmissionapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: CryptoRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Crypto>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Crypto>>>
        get() = _uiState


    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            repository.searchCrypto(_query.value)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun getAllCrypto() {
        viewModelScope.launch {
            repository.getAllCrypto()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { crypto ->
                    _uiState.value = UiState.Success(crypto)
                }
        }
    }
}