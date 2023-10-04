package com.harutyun.data.repository

import com.harutyun.data.mappers.ExchangeRatesMapper
import com.harutyun.data.remote.ExchangeRateRemoteDataSource
import com.harutyun.data.remote.NetworkHandler
import com.harutyun.data.remote.entity.ExchangeRatesResponseNetworkEntity
import com.harutyun.domain.model.Currency
import com.harutyun.domain.model.NetworkResponse
import com.harutyun.domain.repository.ExchangeRatesRepository
import retrofit2.HttpException


class ExchangeRatesRepositoryImpl(
    private val exchangeRateRemoteDataSource: ExchangeRateRemoteDataSource,
    private val exchangeRatesMapper: ExchangeRatesMapper,
    private val networkHandler: NetworkHandler
) : ExchangeRatesRepository {

    override suspend fun getExchangeRatesByBaseCurrencyName(baseCurrencyName: String): NetworkResponse<List<Currency>> {
        return if (networkHandler.isNetworkAvailable()) {
            try {
                val data =
                    exchangeRateRemoteDataSource.getExchangeRatesByBaseCurrency(baseCurrencyName)
                if (data.isSuccessful) {
                    val response: ExchangeRatesResponseNetworkEntity? = data.body()
                    if (response != null) {
                        NetworkResponse.Success(exchangeRatesMapper.mapToDomain(response))
                    } else {
                        NetworkResponse.Failure("No data")
                    }
                } else {
                    NetworkResponse.Failure(data.errorBody().toString())
                }
            } catch (e: HttpException) {
                NetworkResponse.Failure(e.message())
            } catch (e: Throwable) {
                NetworkResponse.Failure("Something went wrong")
            }
        } else NetworkResponse.Failure("No network connection")
    }
}
