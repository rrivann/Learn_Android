package com.dicoding.mysubmissionapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mysubmissionapp.core.data.CryptoRepository
import com.dicoding.mysubmissionapp.core.model.Crypto
import com.dicoding.mysubmissionapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: CryptoRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Crypto>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Crypto>>
        get() = _uiState

    fun getRewardById(cryptoId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getCryptoById(cryptoId))
        }
    }


    fun setFavoriteId(cryptoId: Long, newValue: Boolean) {
        viewModelScope.launch {
            repository.setFavoriteCrypto(cryptoId, newValue)
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getCryptoById(cryptoId))
        }
    }
}