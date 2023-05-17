package com.dicoding.mysubmissionapp.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mysubmissionapp.core.data.CryptoRepository
import com.dicoding.mysubmissionapp.core.model.Crypto
import com.dicoding.mysubmissionapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: CryptoRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Crypto>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Crypto>>>
        get() = _uiState


    fun getAllCryptoFavorite() {
        viewModelScope.launch {
            repository.getAllFavorite()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { crypto ->
                    _uiState.value = UiState.Success(crypto)
                }
        }
    }
}