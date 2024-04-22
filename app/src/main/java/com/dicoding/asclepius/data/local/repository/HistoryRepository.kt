package com.dicoding.asclepius.data.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.asclepius.data.local.HistoryDao
import com.dicoding.asclepius.data.local.HistoryDatabase
import com.dicoding.asclepius.data.local.entity.HistoryResult
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HistoryRepository(application: Application) {
    private val mHistoryDao: HistoryDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = HistoryDatabase.getDatabase(application)
        mHistoryDao = db.historyDao()
    }

    fun getAllHistory(): LiveData<List<HistoryResult>> = mHistoryDao.getHistories()
    fun insert(result: HistoryResult) {
        executorService.execute { mHistoryDao.insert(result) }
    }
}