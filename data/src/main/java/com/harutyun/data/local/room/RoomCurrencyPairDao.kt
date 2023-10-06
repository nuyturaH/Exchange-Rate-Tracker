package com.harutyun.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harutyun.data.local.entity.CURRENCY_TABLE
import com.harutyun.data.local.entity.CurrencyPairEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomCurrencyPairDao {

    @Query("SELECT * FROM $CURRENCY_TABLE")
    fun getItems(): Flow<List<CurrencyPairEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: CurrencyPairEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(item: List<CurrencyPairEntity>)

    @Query("DELETE FROM $CURRENCY_TABLE WHERE baseCurrencyName = :baseCurrencyName AND targetCurrencyName = :targetCurrencyName")
    suspend fun deleteItem(baseCurrencyName: String, targetCurrencyName: String)
}
