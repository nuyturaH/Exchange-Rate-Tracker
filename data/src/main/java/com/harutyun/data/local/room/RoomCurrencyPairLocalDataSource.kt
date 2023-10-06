package com.harutyun.data.local.room

import com.harutyun.data.local.ExchangeRatesLocalDataSource
import com.harutyun.data.local.entity.CurrencyPairEntity
import kotlinx.coroutines.flow.Flow

class RoomCurrencyPairLocalDataSource(private val roomItemDao: RoomCurrencyPairDao) : ExchangeRatesLocalDataSource {

    override fun getCurrencyPairs(): Flow<List<CurrencyPairEntity>> {
        return roomItemDao.getItems()
    }

    override suspend fun addCurrencyPair(item: CurrencyPairEntity) {
        roomItemDao.insertItem(item)
    }

    override suspend fun removeCurrencyPair(item: CurrencyPairEntity) {
        roomItemDao.deleteItem(item.baseCurrencyName, item.targetCurrencyName)
    }

}
