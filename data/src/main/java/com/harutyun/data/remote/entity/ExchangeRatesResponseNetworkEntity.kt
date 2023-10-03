package com.harutyun.data.remote.entity

import com.squareup.moshi.Json

data class ExchangeRatesResponseNetworkEntity(
    @Json(name = "success")
    val success: Boolean = false,

    @Json(name = "timestamp")
    val timestamp: Long = 0,

    @Json(name = "base")
    val base: String = "",

    @Json(name = "date")
    val date: String = "",

    @Json(name = "rates")
    val rates: Map<String, Double> = emptyMap()
)
