package com.harutyun.data.remote

import com.harutyun.data.remote.entity.ExchangeRatesResponseNetworkEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesService {

    @GET("latest")
    fun getExchangeRates(@Query("base") baseCurrencyName: String): Response<ExchangeRatesResponseNetworkEntity>
}
