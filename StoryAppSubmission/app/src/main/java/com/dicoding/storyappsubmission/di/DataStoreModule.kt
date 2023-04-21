package com.dicoding.storyappsubmission.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.storyappsubmission.data.local.preferences.AuthPreferences
import com.dicoding.storyappsubmission.data.local.preferences.ProfilePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "application")

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore

    @Provides
    @Singleton
    fun provideAuthPreferences(dataStore: DataStore<Preferences>): AuthPreferences =
        AuthPreferences(dataStore)

    @Provides
    @Singleton
    fun provideProfilePreferences(dataStore: DataStore<Preferences>): ProfilePreferences =
        ProfilePreferences(dataStore)
}