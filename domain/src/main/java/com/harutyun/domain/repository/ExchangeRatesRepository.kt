package com.harutyun.domain.repository

import com.harutyun.domain.model.Currency
import com.harutyun.domain.model.CurrencyPair
import com.harutyun.domain.model.NetworkResponse
import kotlinx.coroutines.flow.Flow


interface ExchangeRatesRepository {

    suspend fun getExchangeRatesByBaseCurrencyName(baseCurrencyName: String): NetworkResponse<List<Currency>>

    fun getFavorites(): Flow<List<CurrencyPair>>

    suspend fun addFavorite(currencyPair: CurrencyPair)

    suspend fun removeFavorite(currencyPair: CurrencyPair)
}
