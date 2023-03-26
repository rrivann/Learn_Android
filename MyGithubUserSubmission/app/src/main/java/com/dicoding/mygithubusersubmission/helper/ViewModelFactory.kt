package com.dicoding.mygithubusersubmission.helper

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mygithubusersubmission.database.UserRepository
import com.dicoding.mygithubusersubmission.di.Injection
import com.dicoding.mygithubusersubmission.ui.viewmodels.DetailViewModel
import com.dicoding.mygithubusersubmission.ui.viewmodels.MainViewModel

class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }

    }
}