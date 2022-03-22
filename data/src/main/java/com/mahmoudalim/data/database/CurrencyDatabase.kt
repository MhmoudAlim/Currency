package com.mahmoudalim.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Mahmoud Alim on 21/03/2022.
 */

@Database(entities = [HistoryEntity::class], version = 2)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
}