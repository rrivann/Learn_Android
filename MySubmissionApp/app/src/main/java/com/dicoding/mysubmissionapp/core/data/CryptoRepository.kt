package com.dicoding.mysubmissionapp.core.data

import android.util.Log
import com.dicoding.mysubmissionapp.core.model.Crypto
import com.dicoding.mysubmissionapp.core.model.FakeCryptoDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CryptoRepository {

    private val crypto = mutableListOf<Crypto>()

    init {
        if (crypto.isEmpty()) {
            FakeCryptoDataSource.dummyCrypto.forEach {
                crypto.add(it)
            }
        }
    }

    fun getAllCrypto(): Flow<List<Crypto>> {
        return flowOf(crypto)
    }

    fun searchCrypto(query: String): Flow<List<Crypto>> {
        return flowOf(FakeCryptoDataSource.dummyCrypto.filter {
            it.name.contains(query, ignoreCase = true)
        })
    }

    fun getCryptoById(cryptoId: Long): Crypto {
        return crypto.first {
            it.id == cryptoId
        }
    }

    fun setFavoriteCrypto(cryptoId: Long, newValue: Boolean): Flow<Boolean> {
        val index = crypto.indexOfFirst { it.id == cryptoId }
        Log.d("ss", newValue.toString())
        val result = if (index != -1) {
            val resultCrypto = crypto[index]
            crypto[index] = resultCrypto.copy(isFavorite = newValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAllFavorite(): Flow<List<Crypto>> {
        val result = mutableListOf<Crypto>()
        crypto.forEach {
            it.isFavorite && result.add(it)
        }
        return flowOf(result)
    }


    companion object {
        @Volatile
        private var instance: CryptoRepository? = null

        fun getInstance(): CryptoRepository =
            instance ?: synchronized(this) {
                CryptoRepository().apply {
                    instance = this
                }
            }
    }
}