package com.harutyun.data.remote

import com.harutyun.data.remote.entity.ExchangeRatesResponseNetworkEntity
import retrofit2.Response

class ExchangeRatesApiRemoteDataSource(
    private val exchangeRatesService: ExchangeRatesService
) : ExchangeRateRemoteDataSource {
    override suspend fun getExchangeRatesByBaseCurrency(baseCurrencyName: String): Response<ExchangeRatesResponseNetworkEntity> {
        return exchangeRatesService.getExchangeRates(baseCurrencyName)
    }
}
