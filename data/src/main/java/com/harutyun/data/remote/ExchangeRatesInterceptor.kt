package com.harutyun.data.remote

import com.harutyun.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ExchangeRatesInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        val url = req.url.newBuilder()
            .addQueryParameter("apikey", BuildConfig.API_KEY)
            .build()
        req = req.newBuilder()
            .url(url)
            .build()
        return chain.proceed(req)
    }
}
