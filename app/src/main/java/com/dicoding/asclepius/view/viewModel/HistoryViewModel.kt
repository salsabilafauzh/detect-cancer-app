package com.dicoding.asclepius.view.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.local.entity.HistoryResult
import com.dicoding.asclepius.data.local.repository.HistoryRepository

class HistoryViewModel(application: Application) : ViewModel() {
    private val mHistoryRepository: HistoryRepository = HistoryRepository(application)

    fun insert(result: HistoryResult) {
        mHistoryRepository.insert(result)
    }

    fun getHistories(): LiveData<List<HistoryResult>> = mHistoryRepository.getAllHistory()
}