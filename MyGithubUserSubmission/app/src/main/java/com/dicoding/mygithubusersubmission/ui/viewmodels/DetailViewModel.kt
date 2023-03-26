package com.dicoding.mygithubusersubmission.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mygithubusersubmission.database.UserRepository
import com.dicoding.mygithubusersubmission.database.local.entity.FavoriteUserEntity
import kotlinx.coroutines.launch

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getBookmarkedNews(favoriteUser: FavoriteUserEntity) =
        userRepository.getFavoriteUser(favoriteUser)

    fun insertFavorite(favoriteUser: FavoriteUserEntity) {
        viewModelScope.launch {
            userRepository.setUserFavorite(favoriteUser)
        }
    }

    fun deleteFavorite(favoriteUser: FavoriteUserEntity) {
        viewModelScope.launch {
            userRepository.deleteUserFavorite(favoriteUser)
        }
    }

    fun getAllFavorite() = userRepository.getAllFavoriteUser()
}