package com.dicoding.mysubmissionapp.core.di

import com.dicoding.mysubmissionapp.core.data.CryptoRepository

object Injection {
    fun provideRepository(): CryptoRepository {
        return CryptoRepository.getInstance()
    }
}