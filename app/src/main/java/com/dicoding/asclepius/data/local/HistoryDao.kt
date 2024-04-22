package com.dicoding.asclepius.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.asclepius.data.local.entity.HistoryResult

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(result: HistoryResult)

    @Query("SELECT * FROM history ORDER BY id ASC")
    fun getHistories():LiveData<List<HistoryResult>>
}