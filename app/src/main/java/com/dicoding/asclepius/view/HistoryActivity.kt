package com.dicoding.asclepius.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.data.local.entity.HistoryResult
import com.dicoding.asclepius.databinding.ActivityHistoryBinding
import com.dicoding.asclepius.view.adapter.HistoryAdapter
import com.dicoding.asclepius.view.viewModel.HistoryViewModel
import com.dicoding.asclepius.view.viewModel.ViewModelFactory

class HistoryActivity : AppCompatActivity() {
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.topAppBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        historyViewModel = obtainViewModel(this@HistoryActivity)

        historyViewModel.getHistories().observe(this) {
            showHistories(it)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvHistory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvHistory.addItemDecoration(itemDecoration)
    }

    private fun showHistories(data: List<HistoryResult>?) {
        if (data?.isEmpty() == true) {
            binding.noHistory.visibility = View.VISIBLE
        } else {
            val adapter = HistoryAdapter()
            adapter.submitList(data)
            binding.rvHistory.adapter = adapter
        }
    }

    private fun obtainViewModel(historyActivity: HistoryActivity): HistoryViewModel {
        val factory = ViewModelFactory.getInstance(historyActivity.application)
        return ViewModelProvider(historyActivity, factory)[HistoryViewModel::class.java]
    }
}