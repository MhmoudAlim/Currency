package com.mahmoudalim.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Created by Mahmoud Alim on 21/03/2022.
 */

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversionRecord(entity: HistoryEntity)

    @Query("SELECT * FROM history_table WHERE date = :date")
    fun fetchRecordsByDate(date: String): Flow<List<HistoryEntity>>


    @Query("SELECT * FROM history_table")
    fun fetchAllRecords(): Flow<List<HistoryEntity>>
}