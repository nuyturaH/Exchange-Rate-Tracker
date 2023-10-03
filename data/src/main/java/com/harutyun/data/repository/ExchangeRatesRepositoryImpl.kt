package com.harutyun.data.repository

import com.harutyun.data.mappers.ExchangeRatesMapper
import com.harutyun.data.remote.ExchangeRateRemoteDataSource
import com.harutyun.data.remote.NetworkHandler
import com.harutyun.domain.model.ExchangeRates
import com.harutyun.domain.model.NetworkResponse
import com.harutyun.domain.repository.ExchangeRatesRepository


class ExchangeRatesRepositoryImpl(
    private val exchangeRateRemoteDataSource: ExchangeRateRemoteDataSource,
    private val exchangeRatesMapper: ExchangeRatesMapper,
    private val networkHandler: NetworkHandler
) : ExchangeRatesRepository {

    override suspend fun getExchangeRatesByBaseCurrencyName(baseCurrencyName: String): NetworkResponse<ExchangeRates> {
        return if (networkHandler.isNetworkAvailable()) {
            val data = exchangeRateRemoteDataSource.getExchangeRatesByBaseCurrency(baseCurrencyName).body()
            if (data != null) {
                NetworkResponse.Success(exchangeRatesMapper.mapToDomain(data))
            } else {
                NetworkResponse.Failure("No data")
            }
        } else NetworkResponse.Failure("No network connection")
    }
}
