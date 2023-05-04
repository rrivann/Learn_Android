package com.dicoding.storyappsubmission.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfilePreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveAuthProfile(email: String, name: String) {
        dataStore.edit { preferences ->
            preferences[EMAIL] = email
            preferences[NAME] = name
        }
    }

    fun getAuthEmail(): Flow<String?> = dataStore.data.map { preferences ->
        preferences[EMAIL]
    }

    fun getAuthName(): Flow<String?> = dataStore.data.map { preferences ->
        preferences[NAME]
    }

    companion object {
        private val EMAIL = stringPreferencesKey("email")
        private val NAME = stringPreferencesKey("name")
    }
}

