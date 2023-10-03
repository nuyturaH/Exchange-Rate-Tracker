package com.harutyun.data.remote

import com.harutyun.data.remote.entity.ExchangeRatesResponseNetworkEntity
import retrofit2.Response

interface ExchangeRateRemoteDataSource {
    suspend fun getExchangeRatesByBaseCurrency(baseCurrencyName: String): Response<ExchangeRatesResponseNetworkEntity>
}
