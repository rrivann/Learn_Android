package com.dicoding.storyappsubmission.data.remote.api

import com.dicoding.storyappsubmission.BuildConfig
import com.dicoding.storyappsubmission.BuildConfig.API_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        var BASE_URL_MOCK: String? = null
        fun getApiService(): ApiService {

            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor).build()

            val retrofit = Retrofit.Builder().baseUrl(BASE_URL_MOCK ?: API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(client).build()

            return retrofit.create(ApiService::class.java)
        }
    }
}