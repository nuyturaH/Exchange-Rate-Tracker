package com.harutyun.data.local

import com.harutyun.data.local.entity.CurrencyPairEntity
import kotlinx.coroutines.flow.Flow

interface ExchangeRatesLocalDataSource {

    fun getCurrencyPairs(): Flow<List<CurrencyPairEntity>>

    suspend fun addCurrencyPair(item: CurrencyPairEntity)

    suspend fun removeCurrencyPair(item: CurrencyPairEntity)
}