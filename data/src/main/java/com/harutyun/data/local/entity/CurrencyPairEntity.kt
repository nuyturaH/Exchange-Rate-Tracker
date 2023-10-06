package com.harutyun.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CURRENCY_TABLE)
data class CurrencyPairEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val baseCurrencyName: String,
    val targetCurrencyName: String,
    val rate: Double
)

const val CURRENCY_TABLE = "currency_pair_table"