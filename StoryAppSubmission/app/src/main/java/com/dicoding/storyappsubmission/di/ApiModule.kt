package com.dicoding.storyappsubmission.di

import com.dicoding.storyappsubmission.database.remote.api.ApiConfig
import com.dicoding.storyappsubmission.database.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun provideApiService(): ApiService = ApiConfig.getApiService()
}