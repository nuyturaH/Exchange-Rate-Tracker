package com.harutyun.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.harutyun.data.local.entity.CurrencyPairEntity

@Database(entities = [CurrencyPairEntity::class], version = 1, exportSchema = false)
abstract class RoomCurrencyPairDatabase : RoomDatabase() {

    abstract val roomCurrencyPairDao: RoomCurrencyPairDao
}
