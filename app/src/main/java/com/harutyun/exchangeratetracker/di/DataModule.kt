package com.harutyun.exchangeratetracker.di

import android.content.Context
import com.harutyun.data.BuildConfig
import com.harutyun.data.mappers.ExchangeRatesMapper
import com.harutyun.data.remote.ExchangeRateRemoteDataSource
import com.harutyun.data.remote.ExchangeRatesApiRemoteDataSource
import com.harutyun.data.remote.ExchangeRatesInterceptor
import com.harutyun.data.remote.ExchangeRatesService
import com.harutyun.data.remote.NetworkHandler
import com.harutyun.data.repository.ExchangeRatesRepositoryImpl
import com.harutyun.domain.repository.ExchangeRatesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideExchangeRateRemoteDataSource(exchangeRatesService: ExchangeRatesService): ExchangeRateRemoteDataSource {
        return ExchangeRatesApiRemoteDataSource(exchangeRatesService)
    }

    @Provides
    fun provideExchangeRatesMapper(): ExchangeRatesMapper {
        return ExchangeRatesMapper()
    }

    @Provides
    fun provideNetworkHandler(@ApplicationContext context: Context): NetworkHandler {
        return NetworkHandler(context)
    }

    @Provides
    fun provideExchangeRatesRepository(
        exchangeRateRemoteDataSource: ExchangeRateRemoteDataSource,
        exchangeRatesMapper: ExchangeRatesMapper,
        networkHandler: NetworkHandler
    ): ExchangeRatesRepository {
        return ExchangeRatesRepositoryImpl(
            exchangeRateRemoteDataSource,
            exchangeRatesMapper,
            networkHandler
        )
    }

    @Provides
    fun provideExchangeRatesInterceptor(): ExchangeRatesInterceptor {
        return ExchangeRatesInterceptor()
    }


    @Provides
    fun provideOkHttpClient(
        exchangeRatesInterceptor: ExchangeRatesInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(exchangeRatesInterceptor)
            .build()
    }


    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    fun provideExchangeRatesApi(retrofit: Retrofit): ExchangeRatesService {
        return retrofit.create(ExchangeRatesService::class.java)
    }
}