package com.harutyun.data.repository

import com.harutyun.data.local.ExchangeRatesLocalDataSource
import com.harutyun.data.local.entity.CurrencyPairEntity
import com.harutyun.data.mappers.ExchangeRatesMapper
import com.harutyun.data.remote.ExchangeRateRemoteDataSource
import com.harutyun.data.remote.NetworkHandler
import com.harutyun.data.remote.entity.ExchangeRatesResponseNetworkEntity
import com.harutyun.domain.model.Currency
import com.harutyun.domain.model.CurrencyPair
import com.harutyun.domain.model.NetworkResponse
import com.harutyun.domain.repository.ExchangeRatesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.HttpException


class ExchangeRatesRepositoryImpl(
    private val exchangeRateRemoteDataSource: ExchangeRateRemoteDataSource,
    private val exchangeRatesLocalDataSource: ExchangeRatesLocalDataSource,
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
                    if (response != null && response.rates.isNotEmpty()) {
                        val list: List<Currency> = exchangeRatesMapper.mapToDomain(response)
                        val favoriteCurrencyPairs: List<CurrencyPair> = getFavorites().first()

                        val updatedList = list.map { currency ->
                            val isFavorite = favoriteCurrencyPairs.any { currencyPair ->
                                currencyPair.baseCurrencyName == baseCurrencyName && currencyPair.targetCurrencyName == currency.name
                            }

                            currency.copy(isFavorite = isFavorite)
                        }

                        NetworkResponse.Success(updatedList)
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

    override fun getFavorites(): Flow<List<CurrencyPair>> {
        val entityList: Flow<List<CurrencyPairEntity>> =
            exchangeRatesLocalDataSource.getCurrencyPairs()
        return entityList.map { exchangeRatesMapper.mapCurrencyPairEntityListToDomain(it) }
    }

    override suspend fun addFavorite(currencyPair: CurrencyPair) {
        exchangeRatesLocalDataSource.addCurrencyPair(
            exchangeRatesMapper.mapCurrencyPairToData(
                currencyPair
            )
        )
    }

    override suspend fun removeFavorite(currencyPair: CurrencyPair) {
        exchangeRatesLocalDataSource.removeCurrencyPair(
            exchangeRatesMapper.mapCurrencyPairToData(
                currencyPair
            )
        )

    }
}
