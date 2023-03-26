package com.dicoding.mygithubusersubmission.database

import android.util.Log
import androidx.lifecycle.LiveData
import com.dicoding.mygithubusersubmission.database.local.entity.FavoriteUserEntity
import com.dicoding.mygithubusersubmission.database.local.room.FavoriteUserDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository private constructor(private val favoriteUserDao: FavoriteUserDao) {

    fun getFavoriteUser(favoriteUser: FavoriteUserEntity): LiveData<FavoriteUserEntity> =
        favoriteUserDao.getFavoriteUserByUsername(favoriteUser.username)

    suspend fun setUserFavorite(favoriteUser: FavoriteUserEntity) {
        favoriteUserDao.insertFavoriteUser(favoriteUser)
    }

    suspend fun deleteUserFavorite(favoriteUser: FavoriteUserEntity) {
        favoriteUserDao.deleteFavorite(favoriteUser.username)
    }

    fun getAllFavoriteUser(): LiveData<List<FavoriteUserEntity>> =
        favoriteUserDao.getAllFavoriteUser()


    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            newsDao: FavoriteUserDao
        ): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(newsDao).also { instance = it }
        }
    }
}