package com.harutyun.exchangeratetracker.di

import android.content.Context
import androidx.room.Room
import com.harutyun.data.BuildConfig
import com.harutyun.data.local.ExchangeRatesLocalDataSource
import com.harutyun.data.local.room.RoomCurrencyPairDao
import com.harutyun.data.local.room.RoomCurrencyPairDatabase
import com.harutyun.data.local.room.RoomCurrencyPairLocalDataSource
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
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideExchangeRateRemoteDataSource(exchangeRatesService: ExchangeRatesService): ExchangeRateRemoteDataSource {
        return ExchangeRatesApiRemoteDataSource(exchangeRatesService)
    }

    @Provides
    fun provideExchangeRatesMapper(): ExchangeRatesMapper {
        return ExchangeRatesMapper()
    }

    @Singleton
    @Provides
    fun provideNetworkHandler(@ApplicationContext context: Context): NetworkHandler {
        return NetworkHandler(context)
    }

    @Singleton
    @Provides
    fun provideExchangeRatesRepository(
        exchangeRateRemoteDataSource: ExchangeRateRemoteDataSource,
        exchangeRatesLocalDataSource: ExchangeRatesLocalDataSource,
        exchangeRatesMapper: ExchangeRatesMapper,
        networkHandler: NetworkHandler
    ): ExchangeRatesRepository {
        return ExchangeRatesRepositoryImpl(
            exchangeRateRemoteDataSource,
            exchangeRatesLocalDataSource,
            exchangeRatesMapper,
            networkHandler
        )
    }

    @Singleton
    @Provides
    fun provideExchangeRatesInterceptor(): ExchangeRatesInterceptor {
        return ExchangeRatesInterceptor()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        exchangeRatesInterceptor: ExchangeRatesInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(exchangeRatesInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideExchangeRatesApi(retrofit: Retrofit): ExchangeRatesService {
        return retrofit.create(ExchangeRatesService::class.java)
    }

    @Singleton
    @Provides
    fun provideRoomCurrencyPairDao(dataBase: RoomCurrencyPairDatabase): RoomCurrencyPairDao {
        return dataBase.roomCurrencyPairDao
    }

    @Singleton
    @Provides
    fun provideExchangeRatesLocalDataSource(dao: RoomCurrencyPairDao): ExchangeRatesLocalDataSource {
        return RoomCurrencyPairLocalDataSource(dao)
    }

    @Singleton
    @Provides
    fun provideRoomCurrencyPairDatabase(@ApplicationContext context: Context): RoomCurrencyPairDatabase {
        return Room.databaseBuilder(
            context,
            RoomCurrencyPairDatabase::class.java,
            "CurrencyPairDatabase.db"
        )
            .build()
    }
}