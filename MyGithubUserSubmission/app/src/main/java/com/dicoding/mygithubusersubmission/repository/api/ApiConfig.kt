package com.dicoding.mygithubusersubmission.repository.api

import com.dicoding.mygithubusersubmission.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {

            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

            }

            val authInterceptor = Interceptor {
                val req = it.request()
                val requestHeaders = req.newBuilder()
                    .addHeader("Authorization", "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").build()
                it.proceed(requestHeaders)
            }

            val client = OkHttpClient.Builder().addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor).build()

            val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build()

            return retrofit.create(ApiService::class.java)
        }
    }
}