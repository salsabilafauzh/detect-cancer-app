package com.dicoding.asclepius.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.data.local.entity.HistoryResult
import com.dicoding.asclepius.databinding.ItemHistoryBinding

class HistoryAdapter : ListAdapter<HistoryResult, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {
    class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: HistoryResult) {
            binding.apply {
                if (history.isCancer) {
                    containerLabel.setBackgroundColor(Color.GREEN)
                    category.text = "Cancer"
                } else {
                    containerLabel.setBackgroundColor(Color.RED)
                    category.text = "Non - Cancer"
                }
                val scoreCalculate = history.score.toFloat() * 100
                score.text = "$scoreCalculate %"
                tvDate.text = history.createdAt
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = getItem(position)
        holder.bind(history)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryResult>() {
            override fun areItemsTheSame(
                oldItem: HistoryResult,
                newItem: HistoryResult
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: HistoryResult,
                newItem: HistoryResult
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}