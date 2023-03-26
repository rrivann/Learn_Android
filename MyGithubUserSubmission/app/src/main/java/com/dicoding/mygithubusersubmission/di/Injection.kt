package com.dicoding.mygithubusersubmission.di

import android.content.Context
import com.dicoding.mygithubusersubmission.database.UserRepository
import com.dicoding.mygithubusersubmission.database.local.room.FavoriteUserDatabase

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val database = FavoriteUserDatabase.getInstance(context)
        val dao = database.favoriteUserDao()

        return UserRepository.getInstance(dao)
    }
}