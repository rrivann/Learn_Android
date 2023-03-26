package com.dicoding.mygithubusersubmission.helper

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mygithubusersubmission.database.UserRepository
import com.dicoding.mygithubusersubmission.di.Injection
import com.dicoding.mygithubusersubmission.ui.viewmodels.DetailViewModel
import com.dicoding.mygithubusersubmission.ui.viewmodels.MainViewModel
import com.dicoding.mygithubusersubmission.ui.viewmodels.SettingViewModel

class ViewModelSettingFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
